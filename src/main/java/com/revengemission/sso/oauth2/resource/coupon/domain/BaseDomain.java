
package com.revengemission.sso.oauth2.resource.coupon.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class BaseDomain implements Serializable {
    private static final long serialVersionUID = 1L;
    private long id;
    /**
     * 创建时间
     */
    private LocalDateTime dateCreated;
    /**
     * 修改时间
     */
    private LocalDateTime lastModified;
    private Integer recordStatus;
    /**
     * 更改次数/每次修改+1
     */
    private Integer version;
    private String remarks;
    private String additionalData;
}
