package com.demo.ai.task;

import com.demo.ai.entity.JdFruit;
import com.demo.ai.entity.JdPet;
import com.demo.ai.entity.JdPlantbean;
import com.demo.ai.service.JdFruitService;
import com.demo.ai.service.JdPetService;
import com.demo.ai.service.JdPlantbeanService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class ScheduledTask {
    @Autowired
    private JdFruitService jdFruitService;
    @Autowired
    private JdPetService jdPetService;
    @Autowired
    private JdPlantbeanService jdPlantbeanService;
    @Resource
    private RedisTemplate redisTemplate;

    public static Date localDate2Date(LocalDate localDate) {
        if (null == localDate) {
            return null;
        }
        ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
        return Date.from(zonedDateTime.toInstant());
    }

    /*    @Scheduled(initialDelay=1000, fixedDelay = 2000)
        public void task1() {
            System.out.println("延迟1000毫秒后执行，任务执行完2000毫秒之后执行！");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/
/*    @Scheduled(fixedRate = 2000)
    public void task2() {
        System.out.println("延迟1000毫秒后执行，之后每2000毫秒执行一次！");
    }*/
   // @Scheduled(cron = "* 49 18 * * ?")
    @Scheduled(cron = "* 22 2,3 * * ?")
    public void updataUserTodaystatus() {
        JdFruit dFruit = new JdFruit();
        dFruit.setUserTodaystatus("0");
        jdFruitService.updateAll(dFruit);
        JdPet dPet = new JdPet();
        dPet.setUserTodaystatus("0");
        jdPetService.updateAll(dPet);

        JdPlantbean dPlantbean = new JdPlantbean();
        dPlantbean.setUserTodaystatus("0");
        jdPlantbeanService.updateAll(dPlantbean);

    }

    @Scheduled(cron = "* 22 0,1 * * ?")
   //  @Scheduled(cron = "* 47 18 * * ?")
    public void taskQueryMysql() {
        deleteByPrex("user:");
        //4次需要弄5个人
        JdFruit dFruit = new JdFruit();
        dFruit.setUserStatus("1");
     //   dFruit.setCreateTime(localDate2Date(LocalDate.now().plusDays(-1)));
      //  dFruit.setUpdateTime(localDate2Date(LocalDate.now()));
        dFruit.setUserTodaystatus("1");
        dFruit.setTodayEffectcount(null);
        dFruit.setTodaycount(null);
        dFruit.setCount(null);
        //5次需要6个人
        JdPet dPet = new JdPet();
        dPet.setUserStatus("1");
       // dPet.setCreateTime(localDate2Date(LocalDate.now().plusDays(-1)));
       // dPet.setUpdateTime(localDate2Date(LocalDate.now()));
        dPet.setUserTodaystatus("1");
        dPet.setTodayEffectcount(null);
        dPet.setTodaycount(null);
        dPet.setCount(null);
        //3次需要4个人
        JdPlantbean dPlantbean = new JdPlantbean();
        dPlantbean.setUserStatus("1");
      //  dPlantbean.setCreateTime(localDate2Date(LocalDate.now().plusDays(-1)));
      //  dPlantbean.setUpdateTime(localDate2Date(LocalDate.now()));
        dPlantbean.setUserTodaystatus("1");
        dPlantbean.setTodayEffectcount(null);
        dPlantbean.setTodaycount(null);
        dPlantbean.setCount(null);
        List<JdFruit> jdFruitList = jdFruitService.queryAll(dFruit);
        List<JdPet> jdPetList = jdPetService.queryAll(dPet);
        List<JdPlantbean> jdPlantbeanList = jdPlantbeanService.queryAll(dPlantbean);
        int i = 0;
        Set<JdFruit> jdFruitSet = new HashSet<>();
        for (JdFruit jf : jdFruitList) {
            if (i < 4) {
                jdFruitSet.add(jf);

            } else {
                //todo
                for (JdFruit kjfset : jdFruitSet) {
                    for (JdFruit jfset : jdFruitSet) {
                        if (!kjfset.getUserMd5().equals(jfset.getUserMd5())) {
                            redisTemplate.opsForSet().add("user:"+kjfset.getUserMd5(), jfset);
                        }
                    }
                }
                jdFruitSet.clear();
                i = 0;
                jdFruitSet.add(jf);
            }
            i++;
        }
        for (JdFruit kjfset : jdFruitSet) {
            for (JdFruit jfset : jdFruitSet) {
                if (!kjfset.getUserMd5().equals(jfset.getUserMd5())) {
                    redisTemplate.opsForSet().add("user:"+kjfset.getUserMd5(), jfset);
                }
            }
        }
        int j = 0;
        Set<JdPet> jdPetSet = new HashSet<>();
        for (JdPet jf : jdPetList) {
            if (j < 6) {
                jdPetSet.add(jf);

            } else {
                //todo
                for (JdPet kjfset : jdPetSet) {
                    for (JdPet jfset : jdPetSet) {
                        if (!kjfset.getUserMd5().equals(jfset.getUserMd5())) {
                            redisTemplate.opsForSet().add("user:"+kjfset.getUserMd5(), jfset);
                        }
                    }
                }
                jdPetSet.clear();
                j = 0;
                jdPetSet.add(jf);
            }
            j++;
        }
        for (JdPet kjfset : jdPetSet) {
            for (JdPet jfset : jdPetSet) {
                if (!kjfset.getUserMd5().equals(jfset.getUserMd5())) {
                    redisTemplate.opsForSet().add("user:"+kjfset.getUserMd5(), jfset);
                }
            }
        }
        int k = 0;
        Set<JdPlantbean> jdPlantbeanSet = new HashSet<>();
        for (JdPlantbean jf : jdPlantbeanList) {
            if (k < 3) {
                jdPlantbeanSet.add(jf);

            } else {
                //todo
                for (JdPlantbean kjfset : jdPlantbeanSet) {
                    for (JdPlantbean jfset : jdPlantbeanSet) {
                        if (!kjfset.getUserMd5().equals(jfset.getUserMd5())) {
                            redisTemplate.opsForSet().add("user:"+kjfset.getUserMd5(), jfset);
                        }
                    }
                }
                jdPlantbeanSet.clear();
                k = 0;
                jdPlantbeanSet.add(jf);
            }
            k++;
        }
        for (JdPlantbean kjfset : jdPlantbeanSet) {
            for (JdPlantbean jfset : jdPlantbeanSet) {
                if (!kjfset.getUserMd5().equals(jfset.getUserMd5())) {
                    redisTemplate.opsForSet().add("user:"+kjfset.getUserMd5(), jfset);
                }
            }
        }

        //查出全部符合数据
        //分组
        //分别插入redis
        //没更新掉的继续更新redis,可以使用set对象作为存储就不会重复了
        System.out.println("更新状态！");

/*        //查询数据更新数据状态
        for (JdFruit jf : jdFruitList) {
            //
            Set<JdFruit> fruitSet = redisTemplate.boundSetOps("user:"+jf.getUserMd5()).members();
            if (fruitSet.size() == 3) {
                for (JdFruit jdf : fruitSet) {
                    jdf.setUserStatus("0");
                    jdFruitService.update(jdf);
                }
            }
        }
        //查询数据更新数据状态
        for (JdPet jf : jdPetList) {
            //
            Set<JdPet> fruitSet = redisTemplate.boundSetOps("user:"+jf.getUserMd5()).members();
            if (fruitSet.size() == 5) {
                for (JdPet jdf : fruitSet) {
                    jdf.setUserStatus("0");
                    jdPetService.update(jdf);
                }
            }
        }
        //查询数据更新数据状态
        for (JdPlantbean jf : jdPlantbeanList) {
            //
            Set<JdPlantbean> fruitSet = redisTemplate.boundSetOps("user:"+jf.getUserMd5()).members();
            if (fruitSet.size() == 2) {
                for (JdPlantbean jdf : fruitSet) {
                    jdf.setUserStatus("0");
                    jdPlantbeanService.update(jdf);
                }
            }
        }*/

    }
    //合理的把数据库状态更新掉

    /**
     * 根据前缀删除key
     * @param prex
     */
    public void deleteByPrex(String prex) {
        //org.apache.commons.collections.CollectionUtils

        prex = prex+"**";
        Set<String> keys = redisTemplate.keys(prex);
        if (CollectionUtils.isNotEmpty(keys)) {
            redisTemplate.delete(keys);
        }
    }
}