package com.omega.proiects10.user;
import com.omega.proiects10.Encryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServices {
    @Autowired private UserRepository repo;
    public byte checkAccount(User user) throws Exception{ // used in MainController
        //0 - notfound
        //1 - user
        //2 - admin

        Iterable<User> users = repo.findAll();
        for(User i: users){
            if(i.getUsername().equals(user.getUsername()) && i.getPassword().equals(Encryption.encrypt(user.getPassword()))){
                return (byte) (i.isAdmin() ? 2:1);
            }
        }
        return 0;
    }
}
