package com.example.demoes;

import com.alibaba.fastjson.JSON;
import com.example.demoes.entity.Employee;
import com.example.demoes.entity.EmployeeRepository;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

@SpringBootTest
class DemoEsApplicationTests {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    void saveTest() {
        Employee employee = new Employee();
        employee.setId("3");
        employee.setFirstName("xuxu");
        employee.setLastName("zh");
        employee.setAge(26);
        employee.setAbout("i am in peking");
        Employee employee1 = employeeRepository.save(employee);
        System.err.println("add a obj");
        System.out.println(employee1.toString());

    }

    @Test
    void searchTest() {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        // 关键字查询
        boolQueryBuilder.must(QueryBuilders.matchQuery("firstName", "xuxu"));

        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        nativeSearchQueryBuilder.withQuery(boolQueryBuilder);
        QueryBuilder query = nativeSearchQueryBuilder.build().getQuery();
        Page<Employee> search = (Page<Employee>) employeeRepository.search(query);
        System.out.println(search.getTotalElements());
        System.out.println(JSON.toJSONString(search.getContent()));


    }

}
