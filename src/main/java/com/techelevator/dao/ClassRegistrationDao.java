package com.techelevator.dao;
import com.techelevator.model.ClassRegistration;

import java.util.List;

public interface ClassRegistrationDao {
    void registerUserForClass(int userId, int classId);

    void unregisterUserFromClass(int userId, int classId);
    List<ClassRegistration> getRegistrationsByUserId(int userId);

    List<ClassRegistration> getRegistrationsByClassId(int classId);

    boolean isUserRegisteredForClass(int userId, int classId);

    List<Integer> getUserIdsByClassId(int classId);

    List<Integer> getClassIdsByUserId(int userId);


}
