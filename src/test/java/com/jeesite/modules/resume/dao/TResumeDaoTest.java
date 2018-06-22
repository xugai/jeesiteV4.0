package com.jeesite.modules.resume.dao;

import com.jeesite.common.tests.BaseSpringContextTests;
import com.jeesite.modules.config.Application;
import com.jeesite.modules.resume.dao.TResumeDao;
import com.jeesite.modules.resume.entity.TResume;
import com.jeesite.modules.sys.utils.EmpUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@ActiveProfiles("test")
@SpringBootTest(classes=Application.class)
@Rollback(false)
public class TResumeDaoTest extends BaseSpringContextTests {
    @Autowired
    private TResumeDao tResumeDao;
    @Test
    public void findListByOffice() throws Exception {
        TResume resume = new TResume();
        resume.setEmpCode("user17_mqli");
        System.out.println(tResumeDao.findCountByOffice(resume));
        List<TResume> list = tResumeDao.findListByOffice(0,10,resume);
        for(TResume t :list)
            System.out.println(t.toString());
    }

}