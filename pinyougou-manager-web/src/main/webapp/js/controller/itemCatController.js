//控制层
app.controller('itemCatController', function ($scope, $controller, itemCatService) {

    $controller('baseController', {$scope: $scope});//继承

    //读取列表数据绑定到表单中  
    $scope.findAll = function () {
        itemCatService.findAll().success(
            function (response) {
                $scope.list = response;
            }
        );
    };

    //分页
    $scope.findPage = function (page, rows) {
        itemCatService.findPage(page, rows).success(
            function (response) {
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.total;//更新总记录数
            }
        );
    };

    //查询实体
    $scope.findOne = function (id) {
        itemCatService.findOne(id).success(
            function (response) {
                $scope.entity = response;
            }
        );
    };


    //保存
    $scope.save = function () {
        var serviceObject;//服务层对象

        //处理一下parentId字段
        $scope.entity.typeId = $scope.entity.typeId.id;

        if ($scope.entity.id != null) {//如果有ID
            serviceObject = itemCatService.update($scope.entity); //修改
        } else {
            serviceObject = itemCatService.add($scope.entity);//增加
        }
        serviceObject.success(
            function (response) {
                if (response.status) {
                    //重新查询
                    $scope.findByParentId($scope.entity.parentId);//重新加载
                } else {
                    alert(response.message);
                }
            }
        );
    };


    //批量删除
    $scope.dele = function () {
        //获取选中的复选框
        itemCatService.dele($scope.deleIds).success(
            function (response) {
                if (response.status) {
                    $scope.findByParentId($scope.entity.parentId);//重新加载
                    $scope.deleIds = [];
                }else{
                    alert(response.msg)
                }
            }
        );
    };

    $scope.searchEntity = {};//定义搜索对象

    //搜索
    $scope.search = function (page, rows) {
        // itemCatService.search(page, rows, $scope.searchEntity).success(
        //     function (response) {
        //         $scope.list = response.rows;
        //         $scope.paginationConf.totalItems = response.total;//更新总记录数
        //     }
        // );
    };

    $scope.findByParentId = function (parentId) {
        itemCatService.findByParentId(parentId).success(
            function (response) {
                $scope.list = response;
            }
        )
    };

    $scope.typeTemplateList = {data: []};
    $scope.findTypeTemplateList = function () {
        itemCatService.findTypeTemplateList().success(
            function (response) {
                $scope.typeTemplateList = {data: response};
            }
        )
    }

    //记录当前category是第几层
    $scope.level = 1;

    $scope.catLevel = function (level) {
        $scope.level = level;
    };

    $scope.selectList = function (currentEntity) {
        if ($scope.level == 1) {
            $scope.entity1 = null;
            $scope.entity2 = null;
        }

        if ($scope.level == 2) {
            $scope.entity1 = currentEntity;
            $scope.entity2 = null;
        }

        if ($scope.level == 3) {
            $scope.entity2 = currentEntity;
        }

        $scope.findByParentId(currentEntity.id);
        $scope.entity.parentId = currentEntity.id;

    };

    //新增/修改时清理弹出框中的内容
    $scope.clearEntity = function () {

        console.log("itemCat clear:"+$scope.level);
        if ($scope.level == 1) {
            $scope.entity = {parentId:0};
        }

        if ($scope.level == 2) {
            $scope.entity = {parentId:$scope.entity1.id};
        }
//TODO 修改的parentID还有问题
        if ($scope.level == 3) {
            $scope.entity = {parentId:$scope.entity2.id};
        }

    };


    $scope.showTypeId = function (typeId) {
        if(isNaN(typeId)){
            return typeId.id;
        }
        return typeId;
    }


});	
