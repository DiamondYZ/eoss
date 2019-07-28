package com.isesol.wis.operation.vo;

import com.isesol.wis.core.vo.baseVo.BaseVo;
import com.isesol.wis.mybatis.annotation.Update;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "users")
public class User extends BaseVo {
	@Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    @ApiModelProperty(name = "number", value = "编号")
    private String number;

    @Update(bizCode = "basic")
    @ApiModelProperty(name = "name", value = "名称")
    private String name;

    private String account;

    @Update(bizCode = "basic")
    @ApiModelProperty(name = "sex_dict", value = "性别")
    private String sexDict;

    @Update(bizCode = "basic")
    @ApiModelProperty(name = "birthday", value = "生日")
    private Date birthday;

    private String password;

    @ApiModelProperty(name = "certificate_photo_path", value = "证件照")
    private String certificatePhotoPath;

    @Update(bizCode = "basic")
    @ApiModelProperty(name = "identity_type_dict", value = "证件类型")
    private String identityTypeDict;

    @Update(bizCode = "basic")
    @ApiModelProperty(name = "identity_number", value = "证件编号")
    private String identityNumber;

    @Update(bizCode = "basic")
    @ApiModelProperty(name = "phone", value = "电话")
    private String phone;

    @Update(bizCode = "basic")
    @ApiModelProperty(name = "email", value = "邮箱")
    private String email;

    @ApiModelProperty(name = "address", value = "地址")
    private String address;

    @Update(bizCode = "basic")
    @ApiModelProperty(name = "code", value = "识别码")
    private String code;

    @Update(bizCode = "basic")
    @ApiModelProperty(name = "status_dict", value = "员工状态")
    private String statusDict;

    private Date crtTime;

    private Long crtUserId;

    private String crtName;

    private String crtHost;

    private Date updTime;

    private Long updUserId;

    private String updName;

    private String updHost;

    private String owner;

    private String delFlag;

    private String type;

    @ApiModelProperty(name = "description", value = "描述")
    private String description;

    @ApiModelProperty(name = "default_account", value = "默认账户")
    private Long defaultAccount;

    @ApiModelProperty(name = "member_code", value = "成员编号")
    private String memberCode;
}