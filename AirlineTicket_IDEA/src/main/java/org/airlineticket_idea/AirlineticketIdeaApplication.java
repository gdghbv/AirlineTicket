package org.airlineticket_idea;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@MapperScan("org.airlineticket_idea.mapper")
public class AirlineticketIdeaApplication {

	public static void main(String[] args) {
		SpringApplication.run(AirlineticketIdeaApplication.class, args);
	}
	@Bean
	public MybatisPlusInterceptor mybatisPlusInterceptor() {
		MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
		interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL)); //分页
		interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());  //乐观锁
		interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());  //防全局修改和删除
		return interceptor;
	}
}
