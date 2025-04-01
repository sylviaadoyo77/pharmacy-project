/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app;

/**
 *
 * @author hp
 */
import dao.UsersDAO;
import models.Users;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("🔹 Testing Users CRUD Operations...\n");

        // ✅ 1. Add a New User
        Users newUser = new Users(0, "John", "Doe", "johndoe@example.com", "password123", "Admin", LocalDate.now());
        boolean userAdded = UsersDAO.addUser(newUser);
        System.out.println(userAdded ? "✅ User added successfully!\n" : "❌ Failed to add user.\n");

        // ✅ 2. Get and Display All Users
        List<Users> userList = UsersDAO.getAllUsers();
        System.out.println("📌 Users List:");
        for (Users user : userList) {
            System.out.println(user);
        }

        // ✅ 3. Update a User (Change Email)
        if (!userList.isEmpty()) {
            Users updateUser = userList.get(0);
            updateUser.setEmail("newemail@example.com");
            boolean userUpdated = UsersDAO.updateUser(updateUser);
            System.out.println(userUpdated ? "✅ User updated successfully!\n" : "❌ Failed to update user.\n");
        }

        // ✅ 4. Delete a User
        if (!userList.isEmpty()) {
            int userIdToDelete = userList.get(0).getUserId();
            boolean userDeleted = UsersDAO.deleteUser(userIdToDelete);
            System.out.println(userDeleted ? "✅ User deleted successfully!\n" : "❌ Failed to delete user.\n");
        }

        System.out.println("🔹 Users CRUD Test Completed.");
    }
}







