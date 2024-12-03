/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server.dao;

import server.model.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 *
 * @author bhhoa
 */
public class UserDAO extends DAO{

    public UserDAO() {
        super();
    }
    
    public User verifyUser(User user) {
        try {
            PreparedStatement preparedStatement = con.prepareStatement("SELECT *\n"
                    + "FROM users\n"
                    + "WHERE username = ?\n"
                    + "AND password = ?");
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getInt(5),
                        rs.getInt(6),
                        rs.getInt(7),
                        (rs.getInt(8) != 0),
                        (rs.getInt(9) != 0),
                        getRank(rs.getInt(1)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void addUser(User user) {
        try {
            PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO users(username, password, nickname)\n"
                    + "VALUES(?,?,?)");
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getNickname());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public boolean checkDuplicated(String username) {
        try {
            PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM users WHERE username = ?");
            preparedStatement.setString(1, username);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public void updateToOnline(int ID) {
        try {
            PreparedStatement preparedStatement = con.prepareStatement("UPDATE users\n"
                    + "SET IsOnline = 1\n"
                    + "WHERE ID = ?");
            preparedStatement.setInt(1, ID);
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void updateToOffline(int ID) {
        try {
            PreparedStatement preparedStatement = con.prepareStatement("UPDATE users\n"
                    + "SET IsOnline = 0\n"
                    + "WHERE ID = ?");
            preparedStatement.setInt(1, ID);
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void updateToPlaying(int ID) {
        try {
            PreparedStatement preparedStatement = con.prepareStatement("UPDATE users\n"
                    + "SET IsPlaying = 1\n"
                    + "WHERE ID = ?");
            preparedStatement.setInt(1, ID);
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void updateToNotPlaying(int ID) {
        try {
            PreparedStatement preparedStatement = con.prepareStatement("UPDATE users\n"
                    + "SET IsPlaying = 0\n"
                    + "WHERE ID = ?");
            preparedStatement.setInt(1, ID);
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public List<User> getListFriend(int ID) {
        List<User> ListFriend = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = con.prepareStatement("SELECT users.ID, users.NickName, users.IsOnline, users.IsPlaying\n"
                    + "FROM users\n"
                    + "WHERE users.ID IN (\n"
                    + "	SELECT ID_User1\n"
                    + "    FROM friend\n"
                    + "    WHERE ID_User2 = ?\n"
                    + ")\n"
                    + "OR users.ID IN(\n"
                    + "	SELECT ID_User2\n"
                    + "    FROM friend\n"
                    + "    WHERE ID_User1 = ?\n"
                    + ")");
            preparedStatement.setInt(1, ID);
            preparedStatement.setInt(2, ID);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                ListFriend.add(new User(rs.getInt(1),
                        rs.getString(2),
                        (rs.getInt(3) == 1),
                        (rs.getInt(4)) == 1));
            }
            Collections.sort(ListFriend, new Comparator<User>() {
                @Override
                public int compare(User o1, User o2) {
                    if (o1.getIsOnline() && !o2.getIsOnline())
                        return -1;
                    if (o1.getIsPlaying() && !o2.getIsOnline())
                        return -1;
                    if (!o1.getIsPlaying() && o1.getIsOnline() && o2.getIsPlaying() && o2.getIsOnline())
                        return -1;
                    return 0;
                }

            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ListFriend;
    }

    public boolean checkIsFriend(int ID1, int ID2) {
        try {
            PreparedStatement preparedStatement = con.prepareStatement("SELECT Friend.ID_User1\n"
                    + "FROM friend\n"
                    + "WHERE (ID_User1 = ? AND ID_User2 = ?)\n"
                    + "OR (ID_User1 = ? AND ID_User2 = ?)");
            preparedStatement.setInt(1, ID1);
            preparedStatement.setInt(2, ID2);
            preparedStatement.setInt(3, ID2);
            preparedStatement.setInt(4, ID1);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void addFriendShip(int ID1, int ID2) {
        try {
            PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO friend(ID_User1, ID_User2)\n" +
                    "VALUES (?,?)");
            preparedStatement.setInt(1, ID1);
            preparedStatement.setInt(2, ID2);
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void removeFriendship(int ID1, int ID2) {
        try {
            PreparedStatement preparedStatement = con.prepareStatement("DELETE FROM friend\n" +
                    "WHERE (ID_User1 = ? AND ID_User2 = ?)\n" +
                    "OR(ID_User1 = ? AND ID_User2 = ?)");
            preparedStatement.setInt(1, ID1);
            preparedStatement.setInt(2, ID2);
            preparedStatement.setInt(3, ID2);
            preparedStatement.setInt(4, ID1);
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public int getRank(int ID) {
        int rank = 1;
        try {
            PreparedStatement preparedStatement = con.prepareStatement("SELECT users.ID\n"
                    + "FROM battleshipdb.users\n"
                    + "ORDER BY (users.NumberOfGame+users.numberOfDraw*5+users.NumberOfWin*10) DESC");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                if (rs.getInt(1) == ID)
                    return rank;
                rank++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public List<User> getUserStaticRank() {
        List<User> list = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = con.prepareStatement("SELECT *\n"
                    + "FROM battleshipdb.users\n"
                    + "ORDER BY(users.NumberOfGame+users.numberOfDraw*5+users.NumberOfWin*10) DESC\n"
                    + "LIMIT 10");
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                list.add(new User(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getInt(5),
                        rs.getInt(6),
                        rs.getInt(7),
                        (rs.getInt(8) != 0),
                        (rs.getInt(9) != 0),
                        getRank(rs.getInt(1))));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void makeFriend(int ID1, int ID2) {
        try {
            PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO friend(ID_User1,ID_User2)\n"
                    + "VALUES(?,?)");
            preparedStatement.setInt(1, ID1);
            preparedStatement.setInt(2, ID2);
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public int getNumberOfWin(int ID) {
        try {
            PreparedStatement preparedStatement = con.prepareStatement("SELECT users.NumberOfWin\n"
                    + "FROM users\n"
                    + "WHERE users.ID = ?");
            preparedStatement.setInt(1, ID);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    } 

    public int getNumberOfDraw(int ID) {
        try {
            PreparedStatement preparedStatement = con.prepareStatement("SELECT user.NumberOfDraw\n"
                    + "FROM user\n"
                    + "WHERE user.ID = ?");
            preparedStatement.setInt(1, ID);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    public void addDrawGame(int ID) {
        try {
            PreparedStatement preparedStatement = con.prepareStatement("UPDATE user\n"
                    + "SET user.NumberOfDraw = ?\n"
                    + "WHERE user.ID = ?");
            preparedStatement.setInt(1, new UserDAO().getNumberOfDraw(ID) + 1);
            preparedStatement.setInt(2, ID);
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void addWinGame(int ID) {
        try {
            PreparedStatement preparedStatement = con.prepareStatement("UPDATE users\n"
                    + "SET users.numberOfWin = ?\n"
                    + "WHERE users.ID = ?");
            preparedStatement.setInt(1, new UserDAO().getNumberOfWin(ID) + 1);
            preparedStatement.setInt(2, ID);
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public int getNumberOfGame(int ID) {
        try {
            PreparedStatement ps = con.prepareStatement("SELECT users.numberOfWin\n" 
                    + "FROM users\n" 
                    + "WHERE users.ID = ?");
            ps.setInt(1, ID);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return -1;
    }
    
    public void addGame(int ID) {
        try {
            PreparedStatement ps = con.prepareStatement("UPDATE users\n" 
                    + "SET users.numberOfGame = ?\n" 
                    + "WHERE users.ID = ?");
            ps.setInt(1, new UserDAO().getNumberOfGame(ID) + 1);
            ps.setInt(2, ID);
            System.out.println(ps);
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void decreaseGame(int ID) {
        try {
            PreparedStatement ps = con.prepareStatement("UPDATE users\n" 
                    + "SET users.numberOfGame = ?\n" 
                    + "WHERE users.ID = ?");
            ps.setInt(1, new UserDAO().getNumberOfGame(ID)-1);
            ps.setInt(2, ID);
            System.out.println(ps);
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public String getNickNameByID(int ID) {
        try {
            PreparedStatement ps = con.prepareStatement("SELECT users.nickname\n"
                    + "FROM users\n"
                    + "WHERE users.ID = ?");
            ps.setInt(1, ID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
