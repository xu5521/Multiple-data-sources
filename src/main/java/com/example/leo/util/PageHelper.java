package com.example.leo.util;


import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.CaseFormat;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.ReflectUtils;

import javax.validation.constraints.NotNull;
import java.beans.PropertyDescriptor;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Data
@Slf4j
public class PageHelper<T> {
	@NotNull(message = "分页条件不能为空")
	private SimplePage pageParam;
	@NotNull(message = "查询对象至少为空对象")
	private T searchParam;
	private static final String likeprefix = "%";
	private static final String dateSplit = "----";

	public Page buildPage() {
		Page page = new Page(pageParam.getCurrent(), pageParam.getSize());
		if(!pageParam.getIsOrder()){//不需要排序
			return page;
		}
		if (StrUtil.isEmpty(pageParam.getOrderBy())) {
//			page.addOrder(new OrderItem().setColumn("id").setAsc(false));
		} else {
			String orderby = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, pageParam.getOrderBy());
			page.addOrder(new OrderItem().setColumn(orderby).setAsc("asc".equals(pageParam.getOrderType().trim().toLowerCase())));
		}
		return page;
	}

	public QueryWrapper buildWrapper() {
		if (searchParam == null) {
			return Wrappers.query();
		}
		return buildWrapper(searchParam);

	}


	public static QueryWrapper buildWrapper(Object paramObj) {

		QueryWrapper query = Wrappers.query();
		if (paramObj != null) {
			PropertyDescriptor[] beanGetters = ReflectUtils.getBeanGetters(paramObj.getClass());
			for (PropertyDescriptor beanGetter : beanGetters) {
				Object invoke = null;
				try {
					invoke = beanGetter.getReadMethod().invoke(paramObj);
				} catch (Exception e) {
					log.error("get property exception");
				}
				if (invoke == null) {
					continue;
				}
				Object[] objects = Arrays.stream(beanGetter.getName().split(StrUtil.UNDERLINE)).map(v -> CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, v)).toArray();
				String column = ArrayUtil.join(objects, StrUtil.DOT);
				if (beanGetter.getPropertyType() == String.class && StrUtil.isNotBlank(String.valueOf(invoke))) {
					String str = String.valueOf(invoke);

					if (StrUtil.containsAny(str, likeprefix)) {//模糊匹配
						query.like(column, invoke);
					} else if (StrUtil.containsAny(str, dateSplit)) {//日期格式
						Date start = null;
						Date end = null;
						try {
							start = DatePattern.NORM_DATETIME_FORMAT.parse(StrUtil.split(str, "----")[0]);
							end = DatePattern.NORM_DATETIME_FORMAT.parse(StrUtil.split(str, "----")[1]);
						} catch (ParseException e) {
							try {
								start = DateUtil.beginOfDay(DatePattern.NORM_DATE_FORMAT.parse(StrUtil.split(str, "----")[0]));
								end = DateUtil.endOfDay(DatePattern.NORM_DATE_FORMAT.parse(StrUtil.split(str, "----")[1]));
							} catch (ParseException e2) {
								throw new BizException(ErrorCode.DATE_TIME_FORMAT_EXCEPTION);
							}
						}
						query.between(column, start, end);
					} else {
						query.eq(column, invoke);
					}
				} else if (beanGetter.getPropertyType() == Boolean.class || invoke instanceof Enum || invoke instanceof Number) {
					query.eq(column, invoke);
				} else if (invoke instanceof Enum[] && ((Enum[]) invoke).length > 0) {
					List<String> invokeStrList = new ArrayList<>();
					for (Enum o : (Enum[]) invoke) {
						try {
							invokeStrList.add((String) o.getClass().getMethod("getCode").invoke(o));
						} catch (Exception e) {
							throw new BizException("当前枚举没有getCode方法");
						}
					}
					query.in(column, invokeStrList);
				} else if (beanGetter.getPropertyType() == String[].class) {
					Date start = null;
					Date end = null;
					String[] str = (String[]) invoke;

					if (str != null && str.length != 0 && StrUtil.isNotBlank(str[0]) && StrUtil.isNotBlank(str[1])){
						try {
							start = DatePattern.NORM_DATETIME_FORMAT.parse(str[0]);
							end = DatePattern.NORM_DATETIME_FORMAT.parse(str[1]);
						} catch (ParseException e) {
							try {
								start = DateUtil.beginOfDay(DatePattern.NORM_DATE_FORMAT.parse(str[0]));
								end = DateUtil.endOfDay(DatePattern.NORM_DATE_FORMAT.parse(str[1]));
							} catch (ParseException e2) {
								throw new BizException(ErrorCode.DATE_TIME_FORMAT_EXCEPTION);
							}
						}
						query.between(column, start, end);
					}

				}else if (invoke instanceof Number[] && ((Number[]) invoke).length>0) {
					Number[] numbers = (Number[]) invoke;
					if (numbers != null && numbers.length != 0) {
						query.between(column, numbers[0], numbers[1]);
					}
				}
			}
		}
		return query;
	}
}
