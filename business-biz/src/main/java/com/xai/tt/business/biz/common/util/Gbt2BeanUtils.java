package com.xai.tt.business.biz.common.util;

import com.tianan.kltsp.dt.client.vo.CarRealtimeInfo;
import com.tianan.kltsp.protocol.packet.NumberField;
import com.tianan.kltsp.protocol.packet.gbt.DataItem;
import com.tianan.kltsp.protocol.packet.gbt.GbtPacket;
import com.tianan.kltsp.protocol.packet.gbt.VehicleInfoReportDataUnit;
import javafx.beans.binding.ObjectExpression;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Gbt2BeanUtils {
    public static CarRealtimeInfo data2Bean(GbtPacket gbtPacket) {
        CarRealtimeInfo info = new CarRealtimeInfo();
        if (gbtPacket == null) {
            return null;
        }

        info.setVehicleModel(gbtPacket.getVehicleModel());
        info.setTerminalModel(gbtPacket.getTerminalModel());
        info.setTerminalVersion(gbtPacket.getTerminalVersion());
        info.setVin(gbtPacket.getVin());
        info.setUpdataTime(gbtPacket.getDataUnit().getDataTime());
        try {
            VehicleInfoReportDataUnit dataUnit = (VehicleInfoReportDataUnit) gbtPacket.getDataUnit();
            dataItems2bean(info, dataUnit);

        } catch (Exception e) {
            e.printStackTrace();
            return info;
        }

        return info;

    }

    public static Map<String, Object> getReessTempData(GbtPacket gbtPacket) {
        VehicleInfoReportDataUnit dataUnit = (VehicleInfoReportDataUnit) gbtPacket.getDataUnit();
        List<DataItem<?>> dataItems = dataUnit.getDataItems();

        VehicleInfoReportDataUnit.ReessTemp temp = null;
        for (DataItem item : dataItems) {
            if (9 == item.getCode()) {
                temp = (VehicleInfoReportDataUnit.ReessTemp) item.getValue();
                break;
            }
        }

        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> details = new ArrayList<>();
        result.put("amount", temp.getAmount().toString());
        result.put("details", details);
        for (VehicleInfoReportDataUnit.ReessTemp.ReessTempDetail detail : temp.getDetail()) {
            Map<String, Object> rec = new HashMap<>();
            rec.put("number", detail.getNumber());
            // 温度探针个数
            rec.put("probeAmount", detail.getProbeAmount().toString());
            // 各温度探针温度值
            List<String> temperatureList = new ArrayList<>();
            for (NumberField field : detail.getTemperatureList()) {
                temperatureList.add(field.toString());
            }
            rec.put("temperatureList", temperatureList);

            details.add(rec);
        }
        return result;

    }

    //极值数据
    public static Map<String, Object> getExtremumData(GbtPacket gbtPacket) {
        VehicleInfoReportDataUnit dataUnit = (VehicleInfoReportDataUnit) gbtPacket.getDataUnit();
        List<DataItem<?>> dataItems = dataUnit.getDataItems();

        VehicleInfoReportDataUnit.Extremum extremum = null;
        for (DataItem item : dataItems) {
            if (6 == item.getCode()) {
                extremum = (VehicleInfoReportDataUnit.Extremum) item.getValue();
                break;
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("highestVolSubCode", extremum.getHighestVolSubCode().toString());
        result.put("highestVolMonCode", extremum.getHighestVolMonCode().toString());
        result.put("highestVoltage", extremum.getHighestVoltage().getDouble());
        result.put("lowestVolSubCode", extremum.getLowestVolSubCode().toString());
        result.put("lowestVolMonCode", extremum.getLowestVolMonCode().toString());
        result.put("lowestVoltage", extremum.getLowestVoltage().getDouble());
        result.put("highestTempSubCode", extremum.getHighestTempSubCode().toString());
        result.put("highestTempProbeCode", extremum.getHighestTempProbeCode().toString());
        result.put("highestTemp", extremum.getHighestTemp().toString());
        result.put("lowestTempSubCode", extremum.getLowestTempSubCode().toString());
        result.put("lowestTempProbeCode", extremum.getLowestTempProbeCode().toString());
        result.put("lowestTemp", extremum.getLowestTemp().toString());

        return result;
    }

    //获得并转换电机数据单元
    public static Map<String, Object> getMotorData(GbtPacket gbtPacket) {
        VehicleInfoReportDataUnit dataUnit = (VehicleInfoReportDataUnit) gbtPacket.getDataUnit();
        List<DataItem<?>> dataItems = dataUnit.getDataItems();

        VehicleInfoReportDataUnit.Motor motor = null;
        for (DataItem item : dataItems) {
            if (2 == item.getCode()) {
                motor = (VehicleInfoReportDataUnit.Motor) item.getValue();
                break;
            }
        }

        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> details = new ArrayList<>();
        result.put("amount", motor.getAmount());
        result.put("details", details);
        for (VehicleInfoReportDataUnit.Motor.MotorDetail detail : motor.getDetail()) {
            Map<String, Object> rec = new HashMap<>();
            rec.put("number", detail.getNumber());
            rec.put("status", detail.getStatus().toString());
            rec.put("controller", detail.getController().toString());
            rec.put("rpm", detail.getRpm().toString());
            rec.put("torque", detail.getTorque().toString());
            rec.put("temperature", detail.getTemperature().toString());
            rec.put("voltage", detail.getVoltage().toString());
            rec.put("current", detail.getCurrent().toString());
            details.add(rec);
        }

        return result;
    }


    /**
     * 数据单元数据提取到bean<br>
     * 0x01 整车数据<br>
     * 0x02 驱动电机数据<br>
     * 0x03 燃料电池数据<br>
     * 0x04 发动机数据<br>
     * 0x05 车辆位置数据<br>
     * 0x06 极值数据<br>
     * 0x07 报警数据<br>
     * 0x08 可充电储能装置电压数据<br>
     * 0x09 可充电储能装置溫度数据<br>
     * 0x0A 〜0x2F 平台交换协议自定义数据<br>
     * 0x30 〜0x7F 预留<br>
     * 0x80 〜0xFE 用户自定义<br>
     */

    public static void dataItems2bean(CarRealtimeInfo info, VehicleInfoReportDataUnit dataUnit) {
        List<DataItem<?>> dataItems = dataUnit.getDataItems();
        for (DataItem<?> item : dataItems) {
            switch (item.getCode()) {
                case 1:
                    VehicleInfoReportDataUnit.Vehicle vehicle = (VehicleInfoReportDataUnit.Vehicle) item.getValue();
                    info.setStatus(vehicle.getStatus().getInt());
                    info.setChargingState(vehicle.getChargingState().getInt());
                    info.setRuningMode(vehicle.getRuningMode().getInt());
                    info.setSpeed(vehicle.getSpeed().getDouble());
                    info.setMileage(vehicle.getMileage().getDouble());
                    info.setVoltage(vehicle.getVoltage().getDouble());
                    info.setCurrent(vehicle.getCurrent().getDouble());
                    info.setSoc(vehicle.getSoc().getInt());
                    info.setDcdc(vehicle.getDcdc().getInt());
                    info.setGear(vehicle.getGear());
                    info.setResistance(vehicle.getResistance());
                    info.setPedalStatus(vehicle.getPedalStatus().getInt());
                    info.setPedalTravel(vehicle.getPedalTravel().getDouble());

                    break;

                case 2:
                    VehicleInfoReportDataUnit.Motor motor = (VehicleInfoReportDataUnit.Motor) item.getValue();
                    info.setMotorAmount(motor.getAmount());
                    info.setMotorDetails(motor.getDetail());

                    break;

                case 3:
                    break;

                case 4:
                    VehicleInfoReportDataUnit.Engine engine = (VehicleInfoReportDataUnit.Engine) item.getValue();
                    info.setEngineConsume(engine.getConsume().getDouble());
                    info.setEngineSpeed(engine.getSpeed().getInt());
                    info.setEngineStatus(engine.getStatus().getInt());
                    break;

                case 5:
                    VehicleInfoReportDataUnit.Location location = (VehicleInfoReportDataUnit.Location) item.getValue();
                    info.setGpsStatus(location.getValid());
                    Double lat = location.getLat().getDouble();
                    Double lon = location.getLon().getDouble();
                    /*info.setGps(GisTools.wgs2gcj02(lon, lat));
                    District determine = DistrictTools.determine(info.getGps());
                    info.setProvince(determine.getParentCode());
                    info.setCity(determine.getAdcode());*/
                    break;

                case 6:
                    VehicleInfoReportDataUnit.Extremum extremum = (VehicleInfoReportDataUnit.Extremum) item.getValue();
                    info.setHighestTemp(extremum.getHighestTemp().getInt());
                    info.setHighestTempProbeCode(extremum.getHighestTempProbeCode().getInt());
                    info.setHighestTempSubCode(extremum.getHighestTempSubCode().getInt());
                    info.setHighestVolMonCode(extremum.getHighestVolMonCode().getInt());
                    info.setHighestVoltage(extremum.getHighestVoltage().getDouble());
                    info.setHighestVolSubCode(extremum.getHighestVolSubCode().getInt());
                    info.setLowestTemp(extremum.getLowestTemp().getInt());
                    info.setLowestTempProbeCode(extremum.getLowestTempProbeCode().getInt());
                    info.setLowestTempSubCode(extremum.getLowestTempSubCode().getInt());
                    info.setLowestVolMonCode(extremum.getLowestVolMonCode().getInt());
                    info.setLowestVolSubCode(extremum.getLowestVolSubCode().getInt());
                    info.setLowestVoltage(extremum.getLowestVoltage().getDouble());

                    break;

                case 7:
                    VehicleInfoReportDataUnit.Warning warn = (VehicleInfoReportDataUnit.Warning) item.getValue();
                    info.setWarning(warn);
                    break;

                case 8:
                    VehicleInfoReportDataUnit.ReessVoltage reessVoltage = (VehicleInfoReportDataUnit.ReessVoltage) item
                            .getValue();
                    info.setResVolAmount(reessVoltage.getAmount().getInt());
                    info.setResVolDetails(reessVoltage.getDetail());

                    break;

                case 9:
                    VehicleInfoReportDataUnit.ReessTemp reessTemp = (VehicleInfoReportDataUnit.ReessTemp) item.getValue();
                    info.setResTmpAmount(reessTemp.getAmount().getInt());
                    info.setResTmpDetails(reessTemp.getDetail());
                    break;

                default:
                    break;
            }
        }
    }
}
