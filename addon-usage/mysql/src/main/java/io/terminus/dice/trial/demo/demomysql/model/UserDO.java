package io.terminus.dice.trial.demo.demomysql.model;

import java.io.Serializable;

import lombok.Data;

/**
 * @author Effet
 */
@Data
public class UserDO implements Serializable {

    private Long id;

    private String name;
}
