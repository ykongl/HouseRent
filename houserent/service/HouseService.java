package com.hspedu.houserent.service;

import com.hspedu.houserent.domain.House;

/**
 * HouseService.java<=>类【业务层】
 * //定义House[],保存House对象
 * 1.   响应HouseView的调用
 * 2.   完成对房屋信息的各种操作(增删改查crud)
 */
public class HouseService {
    private House[] houses;//保存House对象

    private int houseNums = 1;//记录当前有多少个房屋信息
    private int idCounter = 1;//记录当前的id增长到哪个值了

    public HouseService(int size){//有参构造器
        //new houses
        houses = new House[size];//当创建HouseService对象，指定数组大小
        //为了配合测试列表消息，初始化一个House对象
        houses[0] = new House(1,"jack","17354946037","海淀区",2000,"未出租");
    }
    //list方法，返回houses
    public House[] list(){
        return houses;
    }
    //add 方法，添加新对象，返回boolean
    public boolean add(House newHouse){
        //判断是否可以继续添加(暂时不考虑输扩容的问题)==>(能否自己加入扩容)
        if(houseNums >= houses.length){//已满不能在添加
            System.out.println("数组已满，不能再添加了....");
            return false;
        }
        //把newHouse对象加入到数组，新增加一个房屋
        houses[houseNums++] = newHouse;
        //我们需要设定一个Id自增长的机制,然后跟心newHouse的id
        newHouse.setId(++idCounter);
        return true;
    }
    //delete方法，删除房屋信息
    public boolean delete(int delId){
        //应当先找到要删除的房屋信息对应的下标
        //下标和房屋的编号不是一回事
        int index= -1;
        //houses.length == houseNums
        for (int i = 0; i < houseNums; i++) {
            if(delId == houses[i].getId()){ //要删除的房屋(id)，是数组下表为i的元素
               index = i;
            }
        }
        if(index == -1){//说明delId在数组中不存在
           return false;
        }
        //难点
        for (int i = index; i < houseNums - 1; i++) {
            houses[i] = houses[i + 1];
        }
        houses[--houseNums] = null;//把当前存在的房屋信息的最后一个 设置null

        return true;
    }
    //find 方法，返回House对象或null
    public House findById(int findid){
        //遍历数组
        for (int i = 0; i < houseNums; i++) {
            if(houses[i].getId() == findid){
                return houses[i];

            }
        }
        return null;

    }

}
