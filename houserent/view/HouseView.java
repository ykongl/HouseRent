package com.hspedu.houserent.view;

import com.hspedu.houserent.domain.House;
import com.hspedu.houserent.service.HouseService;
import com.hspedu.houserent.utils.Utility;

/**
 * 1.显示页面
 * 2.接收页面
 * 3.调用HouseService完成对房屋的各种操作
 *
 */
public class HouseView {
    //显示主菜单
    private boolean loop = true;//控制显示菜单
    private char key = ' ';//接受用户输入
    private HouseService houseService = new HouseService(2);//设定数组的大小为10

    //编写listHouses()显示房屋列表
    public void listHouses(){
        System.out.println("===========房屋列表===========");
        System.out.println("编号\t\t房主\t\t电话\t\t地址\t\t月租\t\t状态(未出租/已出租)");
        House[] house = houseService.list();//得到所有房屋信息
        for (int i = 0; i < house.length; i++) {//这里有问题
            if(house[i] == null){
                break;
            }
            System.out.println(house[i]);//house[i].toString
        }
    }
    //编写addHouse() 接受输入，创建House对象,调用add方法
    public void addHouse(){
        System.out.println("===========添加房屋===========");
        System.out.print("姓名：");
        String name = Utility.readString(8);
        System.out.print("电话：");
        String phone = Utility.readString(12);
        System.out.print("地址：");
        String address = Utility.readString(10);
        System.out.print("月租：");
        int rent = Utility.readInt();
        System.out.print("状态：");
        String state = Utility.readString(3);

        //创建一个新的House对象，注意Id 是系统分配的，用户不能输入
        House newHouse = new House(0,name,phone,address,rent,state);
        if(houseService.add(newHouse)){
            System.out.println("===============添加房屋成功===============");
        }else{
            System.out.println("===============添加房屋失败===============");
        }
    }
    //删除房屋信息,接收输入的id,调用Service 的del方法
    public void delHouse(){
        System.out.println("===========删除房屋===========");
        System.out.print("请选择待删除房屋的编号(-1退出)：");
        int delid = Utility.readInt();
        if(delid == -1){
            System.out.println("============放弃删除房屋信息=============");
            return;
        }
        //注意该方法本身就有循环判断的逻辑，必须输入Y/N
        char choice = Utility.readConfirmSelection();
        if(choice == 'Y'){//确认删除
            if(houseService.delete(delid)){
                System.out.println("===========删除成功==========");
            }else{
                System.out.println("============房屋编号不存在，删除失败===============");
            }

        }else{
            System.out.println("============放弃删除房屋信息=============");
        }

    }
    //完成退出功能
    public void exit(){
        char c = Utility.readConfirmSelection();
        if(c == 'Y'){
            loop = false;
        }
    }
    //完成查找功能,根据id查找
    public void findHouse(){

        System.out.println("请选择(1-6)");
        System.out.println("-------------查找房屋信息--------------");
        System.out.println("请输入你要查找的id:");
        int findid = Utility.readInt();
        //调用方法
        House house = houseService.findById(findid);
        if(house != null){
            System.out.println(house);
        }else {
            System.out.println("==========房屋信息不存在===========");
        }
    }

    //根据id修改房屋信息
    public void update(){
        System.out.println("============修改房屋信息============");
        System.out.println("请选择修改房屋的编号(-1表示退出)：");
        int updateId = Utility.readInt();
        if(updateId == -1){
            System.out.println("============放弃修改房屋信息============");
            return;
        }
        //根据输入得到updateId,查找对象
        House house = houseService.findById(updateId);//这里返回的是引用类型【即，数组的元素】
        if(house == null){
            System.out.println("=======修改的房屋信息不存在===========");
            return;
        }

        System.out.println("姓名(" + house.getName() +"):");
        String name = Utility.readString(8,"");//如果用户直接回车表示不修改该信息，默认为""
        if(!"".equals(name)){//修改
            house.setName(name);
        }
        System.out.println("电话(" + house.getPhone() +"):");
        String phone = Utility.readString(12,"");
        if(!"".equals(phone)){//修改
            house.setPhone(phone);
        }
        System.out.println("地址(" + house.getAddress() +"):");
        String address = Utility.readString(12,"");
        if(!"".equals(address)){//修改
            house.setAddress(address);
        }
        System.out.println("租金(" + house.getRent() +"):");
        int rent = Utility.readInt(-1);
        if(rent != -1){//修改
            house.setRent(rent);
        }
        System.out.println("状态(" + house.getState() +"):");
        String state = Utility.readString(3,"");
        if(!"".equals(state)){//修改
            house.setState(state);
        }
        System.out.println("=======修改的房屋信息成功=========");
    }

    //显示主菜单
    public void mainMenu(){

        do {
            System.out.println("--------------房屋出租系统---------------");
            System.out.println("\t\t\t1 新 增 房 源");
            System.out.println("\t\t\t2 查 找 房 屋");
            System.out.println("\t\t\t3 删 除 房 屋");
            System.out.println("\t\t\t4 修 改 房 屋 信 息");
            System.out.println("\t\t\t5 房 屋 列 表");
            System.out.println("\t\t\t6 退       出");
            System.out.print("请输入你的选择1-6：");
            key = Utility.readChar();
            switch (key){
                case '1':
                    addHouse();
                    break;
                case '2':
                    findHouse();
                    break;
                case '3':
                    delHouse();
                    break;
                case '4':
                    update();
                    break;
                case '5':
                    listHouses();
                    break;
                case '6':
                    //使用工具Utility提供方法，完成确认
                    exit();
                    break;
            }

        }while (loop);
    }
}
