package com.revengemission.sso.oauth2.resource.coupon.service.impl;

import com.revengemission.sso.oauth2.resource.coupon.persistence.entity.SequenceEntity;
import com.revengemission.sso.oauth2.resource.coupon.persistence.entity.SequenceEntityExample;
import com.revengemission.sso.oauth2.resource.coupon.persistence.mapper.SequenceEntityMapper;
import com.revengemission.sso.oauth2.resource.coupon.service.SequenceService;
import com.revengemission.sso.oauth2.resource.coupon.utils.WeakHashLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.locks.Lock;

@Service
public class SequenceServiceImpl implements SequenceService {

    @Autowired
    SequenceEntityMapper entityMapper;

    WeakHashLock<String> weakHashLock = new WeakHashLock<>();

    @Override
    public long nextValue(String sequenceName) {
        long[] result = nextValue(sequenceName, 1);
        return result[0];
    }

    @Override
    public long[] nextValue(String sequenceName, int count) {
        Lock lock = weakHashLock.get(sequenceName);
        try {
            lock.lock();
            return generateSequence(sequenceName, count);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public long currentValue(String sequenceName) {
        SequenceEntityExample example = new SequenceEntityExample();
        example.createCriteria().andSequenceNameEqualTo(sequenceName);
        List<SequenceEntity> entityList = entityMapper.selectByExample(example);
        if (entityList != null && entityList.size() > 0) {
            return entityList.get(0).getCurrentValue();
        } else {
            return 0;
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public long[] generateSequence(String sequenceName, int count) {
        SequenceEntityExample example = new SequenceEntityExample();
        example.createCriteria().andSequenceNameEqualTo(sequenceName);
        List<SequenceEntity> entityList = entityMapper.selectByExample(example);
        long begin = 0;
        if (entityList == null || entityList.size() == 0) {
            SequenceEntity entity = new SequenceEntity();
            entity.setSequenceName(sequenceName);
            entity.setCurrentValue((long) count);
            entity.setRecordStatus(0);
            entityMapper.insert(entity);
        } else {
            SequenceEntity entity = entityList.get(0);
            begin = entity.getCurrentValue();
            entity.setCurrentValue(entity.getCurrentValue() + count);
            entity.setLastModified(LocalDateTime.now());
            entityMapper.updateByPrimaryKey(entity);
        }
        long[] result = new long[count];
        for (int i = 1; i <= count; i++) {
            result[i - 1] = begin + i;
        }
        return result;
    }
}
