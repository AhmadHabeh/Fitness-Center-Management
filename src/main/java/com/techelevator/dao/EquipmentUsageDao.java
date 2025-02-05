
package com.techelevator.dao;

import com.techelevator.model.EquipmentUsage;
import java.util.List;

public interface EquipmentUsageDao {

    void logEquipmentUsage(EquipmentUsage usage);

    EquipmentUsage getEquipmentUsageById(int usageId);

    List<EquipmentUsage> getEquipmentUsageBySessionId(int sessionId);

    List<EquipmentUsage> getEquipmentUsageByUserId(int userId);

    List<EquipmentUsage> getAllEquipmentUsage();

    void updateEquipmentUsage(EquipmentUsage usage);

    void deleteEquipmentUsage(int usageId);
}
