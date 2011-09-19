package com.zjut.oa.mvc.action;

import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.zjut.oa.db.Pager;
import com.zjut.oa.mvc.core.ActionAdapter;
import com.zjut.oa.mvc.core.Constant;
import com.zjut.oa.mvc.core.annotation.Fail;
import com.zjut.oa.mvc.core.annotation.Result;
import com.zjut.oa.mvc.core.annotation.Success;
import com.zjut.oa.mvc.domain.Academy;
import com.zjut.oa.mvc.domain.User;
import com.zjut.oa.tool.CalendarTool;

public class UserAction extends ActionAdapter {

	@Result("/WEB-INF/pages/freeze/user/show.jsp")
	public String show(HttpServletRequest req, HttpServletResponse resp) {
		int id = param(req, "id", 0);

		User model = new User();
		if (id != 0) {
			model.setId(id);
			model = model.get(id);
		}

		setAttr(req, MODEL, model);

		return INPUT;

	}

	@Result("/WEB-INF/pages/freeze/user/viewAdd.jsp")
	public String viewAdd(HttpServletRequest req, HttpServletResponse resp) {

		Academy academy = new Academy();
		setAttr(req, PAGE_USER_ACADEMYLIST_KEY, academy.listAll());

		return INPUT;
	}

	@Success(path = "/WEB-INF/pages/freeze/user/viewAdd.jsp")
	@Fail(path = "/WEB-INF/pages/freeze/user/viewAdd.jsp")
	public String add(HttpServletRequest req, HttpServletResponse resp) {
		String uid = param(req, "uid");
		String username = param(req, "username");
		String password = param(req, "password");

		String email = param(req, "email");
		String cornet = param(req, "cornet");
		String telephone = param(req, "telephone");
		int academyID = param(req, "academyID", -1);
		String major = param(req, "major");
		String location = param(req, "location");
		String dormitory = param(req, "dormitory");
		int islock = param(req, "islock", -1);

		User model = new User();
		model.setUid(uid);
		model.setUsername(username);
		model.setPassword(password);
		model.setEmail(email);
		model.setCornet(cornet);
		model.setTelephone(telephone);
		model.setAcademyID(academyID);
		model.setMajor(major);
		model.setLocation(location);
		model.setDormitory(dormitory);
		model.setIslock(islock);

		Academy academy = new Academy();
		setAttr(req, PAGE_USER_ACADEMYLIST_KEY, academy.listAll());

		setAttr(req, MODEL, model);

		if (StringUtils.isBlank(uid)) {
			setAttr(req, TIP_NAME_KEY, "请输入学号");
			return FAIL;
		}
		if (model.existProperty("uid", uid)) {
			setAttr(req, TIP_NAME_KEY, "学号[" + uid + "]已存在");
			return FAIL;
		}

		if (StringUtils.isBlank(username)) {
			setAttr(req, TIP_NAME_KEY, "请输入姓名");
			return FAIL;
		}
		if (StringUtils.isBlank(password)) {
			setAttr(req, TIP_NAME_KEY, "请输入密码");
			return FAIL;
		}
		if (StringUtils.isBlank(email)) {
			setAttr(req, TIP_NAME_KEY, "请输入Email地址");
			return FAIL;
		}
		if (StringUtils.isBlank(cornet)) {
			setAttr(req, TIP_NAME_KEY, "请输入短号");
			return FAIL;
		}
		if (StringUtils.isBlank(telephone)) {
			setAttr(req, TIP_NAME_KEY, "请输入手机号码");
			return FAIL;
		}
		if (academyID == -1) {
			setAttr(req, TIP_NAME_KEY, "请选择学院");
			return FAIL;
		}

		if (StringUtils.isBlank(major)) {
			setAttr(req, TIP_NAME_KEY, "请输入专业班级");
			return FAIL;
		}

		if (StringUtils.isBlank(location)) {
			setAttr(req, TIP_NAME_KEY, "请选择所在校区");
			return FAIL;
		}

		if (StringUtils.isBlank(dormitory)) {
			setAttr(req, TIP_NAME_KEY, "请输入宿舍");
			return FAIL;
		}

		if (islock == -1) {
			setAttr(req, TIP_NAME_KEY, "请选择状态");
			return FAIL;
		}
		Timestamp now = CalendarTool.now();
		model.setAddtime(now);
		model.setModifytime(now);

		if (model.save() > 0) {
			setAttr(req, TIP_NAME_KEY, "添加用户[" + username + "]成功");
			return SUCCESS;
		} else {
			setAttr(req, TIP_NAME_KEY, "添加用户失败");
			return FAIL;
		}

	}

	@Result("/WEB-INF/pages/freeze/user/viewModify.jsp")
	public String viewModify(HttpServletRequest req, HttpServletResponse resp) {
		int id = param(req, "id", 0);

		User model = new User();
		if (id != 0) {
			model.setId(id);
			model = model.get(id);
		}

		setAttr(req, MODEL, model);

		return INPUT;

	}

	@Success(path = "/WEB-INF/pages/freeze/user/viewModify.jsp")
	@Fail(path = "/WEB-INF/pages/freeze/user/viewModify.jsp")
	public String modify(HttpServletRequest req, HttpServletResponse resp) {
		int id = param(req, "id", 0);
		String uid = param(req, "uid");
		String username = param(req, "username");
		String password = param(req, "password");

		User model = new User();
		if (id != 0) {
			model.setId(id);
			model = model.get(id);
		}

		String pre_uid = model.getUid();
		String pre_username = model.getUsername();
		String pre_password = model.getPassword();

		if (StringUtils.isBlank(pre_uid)) {
			setAttr(req, TIP_NAME_KEY, "加载用户失败");
			return FAIL;
		}

		if (StringUtils.equals(pre_uid, uid)
				&& StringUtils.equals(pre_username, username)
				&& StringUtils.equals(pre_password, password)) {
			setAttr(req, TIP_NAME_KEY, "无任何修改");
			model.setUid(pre_uid);
			model.setUsername(pre_username);
			model.setPassword(pre_password);
			setAttr(req, MODEL, model);
			return FAIL;
		}

		if (StringUtils.isBlank(uid)) {
			setAttr(req, TIP_NAME_KEY, "请输入学号");
			model.setUid(pre_uid);
			model.setUsername(pre_username);
			model.setPassword(pre_password);
			setAttr(req, MODEL, model);
			return FAIL;
		}
		if (StringUtils.isBlank(username)) {
			setAttr(req, TIP_NAME_KEY, "请输入姓名");
			model.setUid(pre_uid);
			model.setUsername(pre_username);
			model.setPassword(pre_password);
			setAttr(req, MODEL, model);
			return FAIL;
		}
		if (StringUtils.isBlank(password)) {
			setAttr(req, TIP_NAME_KEY, "请输入密码");
			model.setUid(pre_uid);
			model.setUsername(pre_username);
			model.setPassword(pre_password);
			setAttr(req, MODEL, model);
			return FAIL;
		}

		if (model.existProperty("uid", uid)
				&& !StringUtils.equals(pre_uid, uid)) {
			setAttr(req, TIP_NAME_KEY, "学号[" + uid + "]已存在");
			model.setUid(pre_uid);
			model.setUsername(pre_username);
			model.setPassword(pre_password);
			setAttr(req, MODEL, model);
			return FAIL;
		}

		model.setUid(uid);
		model.setUsername(username);
		model.setPassword(password);
		model.setModifytime(CalendarTool.now());
		setAttr(req, MODEL, model);

		if (model.save() > 0) {
			StringBuilder tip = new StringBuilder();
			tip.append("编辑用户成功; ");
			if (!StringUtils.equals(pre_uid, uid))
				tip.append("学号[" + pre_uid + "]->[" + uid + "]; ");
			if (!StringUtils.equals(pre_username, username))
				tip.append("姓名[" + pre_username + "]->[" + username + "]; ");
			if (!StringUtils.equals(pre_password, password))
				tip.append("密码[" + pre_password + "]->[" + password + "]; ");
			setAttr(req, TIP_NAME_KEY, tip.toString());
			return SUCCESS;
		} else {
			setAttr(req, TIP_NAME_KEY, "编辑用户[" + pre_uid + "]失败");
			return FAIL;
		}
	}

	@SuppressWarnings("unchecked")
	@Result("/WEB-INF/pages/freeze/user/filter.jsp")
	public String filter(HttpServletRequest req, HttpServletResponse resp) {
		String uid = param(req, "uid");
		String username = param(req, "username");

		String by = param(req, "by");
		String order = param(req, "order");

		User model = new User();
		model.setUid(uid);
		model.setUsername(username);

		setAttr(req, MODEL, model);

		StringBuilder filter = new StringBuilder();
		if (StringUtils.isNotBlank(uid)) {
			filter.append(" where uid like '%" + uid + "%'");
		}

		if (StringUtils.isNotBlank(uid) && StringUtils.isNotBlank(username)) {
			filter.append(" and username like '%" + username + "%'");
		} else if (StringUtils.isBlank(uid) && StringUtils.isNotBlank(username)) {
			filter.append(" where username like '%" + username + "%'");
		}

		if (StringUtils.isNotBlank(by)
				&& (by.equals("id") || by.equals("uid")
						|| by.equals("username") || by.equals("addtime") || by
						.equals("modifytime"))) {
			if (StringUtils.isNotBlank(order)
					&& (order.equals("asc") || order.equals("desc"))) {
				filter.append(" order by " + by + " " + order);
			} else {
				filter.append(" order by " + by + " asc");
			}
		} else {
			filter.append(" order by id asc");
		}

		// 前台分页
		int p = Constant.DEFAULT_CURRENT_PAGE;
		int countPerPage = Constant.DEFAULT_COUNT_PER_PAGE;
		try {
			p = param(req, "page", Constant.DEFAULT_CURRENT_PAGE);
			if (p < 1)
				p = Constant.DEFAULT_CURRENT_PAGE;
		} catch (NumberFormatException e) {
			p = Constant.DEFAULT_CURRENT_PAGE;
		}
		try {
			countPerPage = param(req, "countPerPage",
					Constant.DEFAULT_COUNT_PER_PAGE);
		} catch (NumberFormatException e) {
			countPerPage = Constant.DEFAULT_COUNT_PER_PAGE;
		}
		int currentPage = p;
		int totalCount = model.totalCount(filter.toString());
		Pager pager = new Pager(currentPage, countPerPage, totalCount);
		// 针对可能的原访问页数大于实际总页数，此处重置下
		if (currentPage > pager.getTotalPage())
			currentPage = p = pager.getTotalPage();
		// 读取部分数据
		List<User> dataList = (List<User>) model.filterByPage(
				filter.toString(), p, pager.getCountPerPage());

		setAttr(req, CURRENT_PAGE_KEY, currentPage);
		setAttr(req, CURRENT_COUNT_PER_PAGE_KEY, countPerPage);
		setAttr(req, PAGER_KEY, pager);
		setAttr(req, MAX_PAGERSHOW_LENGTH_KEY, DEFAULT_MAX_PAGERSHOW_LENGTH);

		setAttr(req, DATA_LIST, dataList);

		return INPUT;
	}

	@Result("/WEB-INF/pages/freeze/user/filter.jsp")
	public String batchDelete(HttpServletRequest req, HttpServletResponse resp) {
		String[] deleteId = params(req, "deleteId");
		if (deleteId.length == 0) {
			setAttr(req, TIP_NAME_KEY, "请选择要删除的用户");
			return this.filter(req, resp);
		}
		// 删除自己检测?
		HttpSession session = req.getSession();
		String[] loginUser = StringUtils.split(
				(String) getAttr(session, Constant.LOGIN_USER_KEY), "&");
		String id = loginUser[0];
		boolean hasMyself = false;
		for (String current_id : deleteId) {
			if (StringUtils.equals(current_id, id)) {
				hasMyself = true;
				break;
			}
		}
		if (hasMyself) {
			setAttr(req, TIP_NAME_KEY, "怎么可以删除自己，您傻了吧！驳回~");
			return this.filter(req, resp);
		}

		User model = new User();
		int[] results = model.batchDelete(deleteId);
		log.debug("batchDelete results[0]: " + results[0]);
		if (results.length > 0 && results[0] > 0) {
			setAttr(req, TIP_NAME_KEY, "成功删除" + results[0] + "个用户");
		}
		return this.filter(req, resp);
	}

}
