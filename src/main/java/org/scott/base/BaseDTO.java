package org.scott.base;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.Timestamp;

/**
 * project name  my-eladmin-backend-v2
 * filename  BaseDTO
 * @author liscott
 * @date 2023/1/4 15:34
 * description  dto基类
 */
@Getter
@Setter
public class BaseDTO implements Serializable {
    private String createBy;
    private String updateBy;

    private Timestamp createTime;
    private Timestamp updateTime;

    @Override
    public String toString() {
        ToStringBuilder builder = new ToStringBuilder(this);
        Field[] fields = this.getClass().getFields();
        try{
            for (Field f : fields) {
                f.setAccessible(true);
                builder.append(f.getName(), f.get(this)).append("\n");
            }
        }catch (Exception e){
            builder.append("toString builder encounter an error");
        }
        return builder.toString();
    }

}
