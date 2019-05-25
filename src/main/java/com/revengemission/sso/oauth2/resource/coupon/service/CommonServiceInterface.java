package com.revengemission.sso.oauth2.resource.coupon.service;


import com.revengemission.sso.oauth2.resource.coupon.domain.NotImplementException;

import java.util.List;

public interface CommonServiceInterface<T> {


    default List<T> list(int pageNum,
                         int pageSize,
                         String sortField,
                         String sortOrder) {
        throw new NotImplementException();
    }

    default long count() {
        throw new NotImplementException();
    }

    default T create(T t) {
        throw new NotImplementException();
    }

    default T retrieveById(long id) throws NotImplementException {
        throw new NotImplementException();
    }

    default T updateById(T t) throws NotImplementException {
        throw new NotImplementException();
    }

    default void deleteById(long id) {
        throw new NotImplementException();
    }

    default void updateRecordStatus(long id, int recordStatus) {
        throw new NotImplementException();
    }
}
