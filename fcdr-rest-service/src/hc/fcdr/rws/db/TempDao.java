package hc.fcdr.rws.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import hc.fcdr.rws.domain.User;

public class TempDao
{
    public List<User> getAllUsers()
    {
        List<User> userList = null;

        try
        {
            File file = new File("Users.dat");

            if (!file.exists())
            {
                User user = new User(1, "Zoltan", "Teacher");
                userList = new ArrayList<User>();
                userList.add(user);
                saveUserList(userList);
            }
            else
            {
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                userList = (List<User>) ois.readObject();
                ois.close();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }

        return userList;
    }

    public User getUser(int id)
    {
        List<User> users = getAllUsers();

        for (User user : users)
            if (user.getId() == id)
                return user;

        return null;
    }

    public int addUser(User pUser)
    {
        List<User> userList = getAllUsers();
        boolean userExists = false;

        for (User user : userList)
            if (user.getId() == pUser.getId())
            {
                userExists = true;
                break;
            }

        if (!userExists)
        {
            userList.add(pUser);
            saveUserList(userList);
            return 1;
        }

        return 0;
    }

    public int updateUser(User pUser)
    {
        List<User> userList = getAllUsers();

        for (User user : userList)
            if (user.getId() == pUser.getId())
            {
                int index = userList.indexOf(user);
                userList.set(index, pUser);
                saveUserList(userList);
                return 1;
            }

        return 0;
    }

    public int deleteUser(int id)
    {
        List<User> userList = getAllUsers();

        for (User user : userList)
            if (user.getId() == id)
            {
                int index = userList.indexOf(user);
                userList.remove(index);
                saveUserList(userList);
                return 1;
            }

        return 0;
    }

    private void saveUserList(List<User> userList)
    {
        try
        {
            File file = new File("Users.dat");
            FileOutputStream fos;

            fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(userList);
            oos.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    // private void a()
    // {
    // if (classificationId != null)
    // {
    // // Update classification.
    // String[] columns1 =
    // { "classification_number", "classification_name",
    // "classification_type" };
    //
    // String questionmarks1 = StringUtils.repeat("?,",
    // columns1.length);
    // questionmarks1 = (String) questionmarks1.subSequence(0,
    // questionmarks1.length() - 1);
    //
    // String query1 = "update " + schema + "."
    // + "classification set "
    // + "classification_number = COALESCE(?, classification_number), "
    // + "classification_name = COALESCE(?, classification_name), "
    // + "classification_type = COALESCE(?, classification_type) "
    // + "where classification_id = ?";
    //
    // List<Object> classificationFieldList = productUpdateRequest.getClassificationFieldList();
    // classificationFieldList.add(classificationId);
    //
    // executeUpdate(query1, classificationFieldList.toArray());
    // }
    // }

}
