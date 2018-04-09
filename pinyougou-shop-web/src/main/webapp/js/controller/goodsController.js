//控制层
app.controller('goodsController', function ($scope, $controller, goodsService, uploadService, itemCatService, typeTemplateService) {

    $controller('baseController', {$scope: $scope});//继承

    $scope.initEntity = function () {
        $scope.entity = {goods: {}, goodsDesc: {itemImages: []}};

    };

    //读取列表数据绑定到表单中
    $scope.findAll = function () {
        goodsService.findAll().success(
            function (response) {
                $scope.list = response;
            }
        );
    };

    //分页
    $scope.findPage = function (page, rows) {
        goodsService.findPage(page, rows).success(
            function (response) {
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.total;//更新总记录数
            }
        );
    };

    //查询实体
    $scope.findOne = function (id) {
        goodsService.findOne(id).success(
            function (response) {
                $scope.entity = response;
            }
        );
    };

    //保存
    $scope.save = function () {

        $scope.entity.goodsDesc.introduction = editor.html();
        var serviceObject;//服务层对象
        if ($scope.entity.id != null) {//如果有ID
            serviceObject = goodsService.update($scope.entity); //修改
        } else {
            serviceObject = goodsService.add($scope.entity);//增加
        }
        serviceObject.success(
            function (response) {
                if (response.status) {
                    //置空当前entity
                    $scope.initEntity();
                    editor.html('');
                } else {
                    alert(response.msg);
                }
            }
        );
    };

    //批量删除
    $scope.dele = function () {
        //获取选中的复选框
        goodsService.dele($scope.selectIds).success(
            function (response) {
                if (response.status) {
                    $scope.reloadList();//刷新列表
                    $scope.selectIds = [];
                }
            }
        );
    };

    $scope.searchEntity = {};//定义搜索对象

    //搜索
    $scope.search = function (page, rows) {
        goodsService.search(page, rows, $scope.searchEntity).success(
            function (response) {
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.total;//更新总记录数
            }
        );
    };

    $scope.uploadFile = function () {
        uploadService.uploadFile().success(
            function (response) {
                if (response.status) {
                    $scope.image_entity.url = response.msg;
                } else {
                    alert(response.msg);
                }
            }
        )
    };

    $scope.addPicEntity = function () {
        $scope.entity.goodsDesc.itemImages.push($scope.image_entity);
    };

    $scope.removePicEntity = function (index) {
        console.log(index);
        $scope.entity.goodsDesc.itemImages.splice(index, 1);
    };

    /**
     * 商品分类处理
     */
    $scope.findItemCat1List = function () {
        itemCatService.findByParentId(0).success(
            function (response) {
                $scope.itemCat1List = response;
                //$scope.entity.goods.category1Id
            }
        )
    };

    $scope.$watch("entity.goods.category1Id", function (newValue, oldValue) {
        if (newValue == undefined) {
            return
        }
        $scope.itemCat3List = null;

        itemCatService.findByParentId(newValue).success(
            function (response) {
                $scope.itemCat2List = response;
            }
        )
    });

    $scope.$watch("entity.goods.category2Id", function (newValue, oldValue) {
        if (newValue == undefined) {
            return
        }
        itemCatService.findByParentId(newValue).success(
            function (response) {
                $scope.itemCat3List = response;
            }
        )
    });

    //读取模板ID
    $scope.$watch('entity.goods.category3Id', function (newValue, oldValue) {
        if (newValue == undefined) {
            return
        }
        itemCatService.findOne(newValue).success(
            function (response) {
                $scope.entity.goods.typeTemplateId = response.typeId;
                $scope.findBrand(response.typeId);
            }
        );
    });

    $scope.findBrand = function (typeId) {
        typeTemplateService.findOne(typeId).success(
            function (response) {
                $scope.typeTemplate = response;// 模板对象
                $scope.brandList = JSON.parse($scope.typeTemplate.brandIds);

                //扩展属性
                $scope.entity.goodsDesc.customAttributeItems = JSON.parse($scope.typeTemplate.customAttributeItems);
            }
        )
    }

});
