package com.aaryan.coronaUtility.service.Repository;

import com.aaryan.coronaUtility.service.Controller.Model.DistrictExcelData.DistrictExcelData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExcelSheetRepository extends JpaRepository<DistrictExcelData,Long> {

}
