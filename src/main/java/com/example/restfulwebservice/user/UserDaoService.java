package com.example.restfulwebservice.user;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

// 일반 클래스가 아닌 어떤 용도로 사용될지 명시해야함.
// Spring FrameWork가 UserDaoService를 메모리 등록을할때 어떤 용도인지 정확하게 알게됨
// 다른쪽에 주입할때 정확하게 사용가능함.
// 명확하지 않을 경우 @Component
@Service
public class UserDaoService {
    private static List<User> users = new ArrayList<>();
    private static int userCount = 3;

    static {
        users.add(new User(1,"Kenneth", new Date(), "pass1","701010-1111111"));
        users.add(new User(2,"Alice", new Date(), "pass2","801010-22222222"));
        users.add(new User(3,"Elena", new Date(), "pass3","901010-1111111"));
    }

    public List<User> findAll() {
        return users;
    }

    public User save(User user) {
        if(user.getId() == null) {
            user.setId(++userCount);
        }

        users.add(user);
        return user;
    }
    public User findOne(int id) {
        for(User user:users) {
            if(user.getId() == id ){
                return user;
            }
        }

        return null;
    }

    public User deleteById(int id) {
        Iterator<User> iterator = users.iterator();
        while(iterator.hasNext()) {
            User user = iterator.next();

            if (user.getId() == id) {
                iterator.remove();
                return user;
            }
        }

        return null;
    }

    public User update(User inputuser){
        for(User user:users) {
            if(user.getId() == inputuser.getId() ){
                user.setName(inputuser.getName());
                user.setJoinDate(inputuser.getJoinDate());
                return user;
            }
        }
        return null;
    }
}
