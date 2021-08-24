package io.terminus.dice.trial.demo.demomysql.dao;

import java.util.List;

import io.terminus.dice.trial.demo.demomysql.model.UserDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

/**
 * @author Effet
 */
@Mapper
public interface UserMapper {

    @Insert("insert into demo_users(name) values (#{name})")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    void insert(UserDO item);

    @Select("select id, name from demo_users")
    List<UserDO> selectAll();
}
