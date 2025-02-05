
package com.techelevator.dao;

import com.techelevator.model.UserDetails;

public interface UserDetailsDao {

    UserDetails getUserDetailsByUserId(int userId);

    void createUserDetails(UserDetails userDetails);

    void updateUserDetails(UserDetails userDetails);

    void deleteUserDetails(int userId);
}
