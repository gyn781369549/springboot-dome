package com.roncoo.education.controller;

import java.io.IOException;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.roncoo.education.bean.User;
import com.roncoo.education.bean.UserExample;
import com.roncoo.education.mapper.UserMapper;
import com.roncoo.education.protobuf.UserProto;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
public class ApiController extends ControllerUtil {
	@Autowired
	private UserMapper userMapper;

	@ApiOperation(value = "list", notes = "")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public List<User> get() {
		UserExample te = new UserExample();
		te.setDistinct(false);
		te.setLimit(100000);
		te.setOffset(0);
		te.setOrderByClause("id");
		return userMapper.selectByExample(te);
	}

	@ApiOperation(value = "selectId", notes = "通过id查找")
	@RequestMapping(value = "/selectIdwq", method = RequestMethod.POST)
	public void selectIdwq(@RequestParam(defaultValue = "1") Integer id, HttpServletResponse response, boolean b)
			throws IOException {
		User u = userMapper.selectByPrimaryKey(id);
		UserProto.Response.Builder personBuilder = UserProto.Response.newBuilder();
		personBuilder.setInfo(u.getName());
		personBuilder.setResult(u.getId());
		personBuilder.setId(id);
		writeTo(personBuilder.build().toByteArray(), response);
	}

	@ApiOperation(value = "添加user", notes = "添加user")
	@RequestMapping(value = "/save", method = RequestMethod.GET)
	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	public String saveU(@RequestParam(defaultValue = "1") Integer id,
			@RequestParam(defaultValue = "name") String name) {
		User u = new User();
		u.setId(id);
		u.setName(name);
		userMapper.insert(u);
		return "true";
	}

}
