package com.jeesite.modules.comment.dao;

import com.jeesite.common.tests.BaseSpringContextTests;
import com.jeesite.modules.comment.entity.CmsComment;
import com.jeesite.modules.comment.service.CmsCommentService;
import com.jeesite.modules.config.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;
@ActiveProfiles("test")
@SpringBootTest(classes=Application.class)
@Rollback(false)
public class CmsCommentDaoTest extends BaseSpringContextTests {
    @Autowired
    private CmsCommentService service;
    @Test
    public void findByGusetbook() throws Exception {
        CmsComment cmsComment = new CmsComment();
        cmsComment.setId("12345");
        cmsComment=service.get(cmsComment);
        List<CmsComment> list = service.findByBooks(cmsComment);
        System.out.println("sddsa");
    }

}