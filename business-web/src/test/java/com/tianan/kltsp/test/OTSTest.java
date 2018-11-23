/*package com.tianan.kltsp.test;

import com.xai.tt.business.App;
import com.tianan.kltsp.dt.client.vo.VehicleInfoReportPayload;
import com.tianan.kltsp.ots.service.BaseOTSManager;
import com.tianan.kltsp.ots.service.OTSService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class OTSTest {

    private static final String endPoint = "http://tsp.cn-beijing.ots.aliyuncs.com";
    private static final String accessKeyId = "LTAI7p54fC0OHWZC";
    private static final String accessKeySecret = "uyvqJ9gxQWC0X4f1q2efkidOIfCv3K";
    private static final String instanceName = "tsp";


    @Autowired
    private BaseOTSManager ots;
    @Autowired
    private OTSService otsService;

    @Before
    public void init() {

    }

    @Test
    public void queryCarData() {

        VehicleInfoReportPayload start = new VehicleInfoReportPayload();
        start.setVin("LIMTESTER66666666");

        //otsService.getRange(start,start,)

    }
}
*/