//控制层
app.controller('goodsController', function ($scope, $controller, goodsService, uploadService, itemCatService, typeTemplateService) {

    $controller('baseController', {$scope: $scope});//继承

    $scope.initEntity = function () {
        $scope.entity = {goods: {}, goodsDesc: {itemImages: [], specificationItems: []}};

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
            }
        );
    });

    //查询品牌下拉列表内容
    $scope.$watch('entity.goods.typeTemplateId', function (typeTemplateId, oldValue) {
        if (typeTemplateId == undefined) {
            return
        }
        typeTemplateService.findOne(typeTemplateId).success(
            function (response) {
                $scope.typeTemplate = response;// 模板对象
                $scope.brandList = JSON.parse($scope.typeTemplate.brandIds);

                //扩展属性
                $scope.entity.goodsDesc.customAttributeItems = JSON.parse($scope.typeTemplate.customAttributeItems);
                //需要将规格重置
                $scope.entity.goodsDesc.specificationItems = [];
                $scope.entity.itemList = [];
            }
        );

        /**
         * [{"attributeName":"网络制式",
         * "attributeValue":["移动3G","移动4G"]},
         * {"attributeName":"屏幕尺寸","
         * attributeValue":["6寸","5.5寸"]}]
         */
        typeTemplateService.findSpecList(typeTemplateId).success(
            function (response) {
                $scope.specList = response;
            }
        )
    });

    $scope.searchSpecByKey = function (attributeName) {
        for (var i = 0; i < $scope.entity.goodsDesc.specificationItems.length; i++) {
            var spec = $scope.entity.goodsDesc.specificationItems[i];
            if (spec.attributeName == attributeName) {
                //如果已经存在这个名字的spec,则将它拿出来
                return spec;
            }
        }
        return null;
    };

    /**
     * @param specificationItem 规格条目
     * @param attributeName 将要存到哪个key中
     * @param attributeValue 存入的对应值
     */
    $scope.addSpec = function (specificationItem, attributeName, attributeValue) {
        //如果之前没有spec,则创建一个
        if (specificationItem == null) {
            specificationItem = {"attributeName": attributeName, attributeValue: [attributeValue]};
            // specificationItem.attributeName = attributeName;
            // specificationItem.attributeValue.push(attributeValue);

            $scope.entity.goodsDesc.specificationItems.push(specificationItem);
        } else {
            //如果之前存在,则直接将值push到数组中
            specificationItem.attributeValue.push(attributeValue);
        }
    };

    /**
     * 按以下格式保存规格
     * [{“attributeName”:”规格名称”,”attributeValue”:[“规格选项1”,“规格选项2”.... ]  } , ....  ]
     *
     * @param node checkbox对象
     * @param attributeName 将要存到哪个key中
     * @param attributeValue 存入的对应值
     */
    $scope.updateSpecAttribute = function (node, attributeName, attributeValue) {
        var specificationItem = $scope.searchSpecByKey(attributeName);
        if (node.checked) {
            //往goodsDesc.specificationItems加入找到的specItem
            $scope.addSpec(specificationItem, attributeName, attributeValue);
        } else {
            var number = specificationItem.attributeValue.indexOf(attributeValue);
            specificationItem.attributeValue.splice(number, 1);
            //规格属性如果没勾选则删掉该规格
            if (specificationItem.attributeValue.length == 0) {
                $scope.entity.goodsDesc.specificationItems.splice(
                    $scope.entity.goodsDesc.specificationItems.indexOf(specificationItem), 1
                )
            }
        }
        //创建sku列表
        $scope.createItemList();
    };
    //创建SKU列表
    $scope.createItemList = function () {
        $scope.entity.itemList = [{spec: {}, price: 200, num: 99, status: '1', isDefault: '0'}];//列表初始化

        var items = $scope.entity.goodsDesc.specificationItems;

        for (var i = 0; i < items.length; i++) {
            $scope.entity.itemList = addColumn($scope.entity.itemList, items[i].attributeName, items[i].attributeValue);
        }
    };

    function addColumn(list, columnName, columnValues) {
        var newList = [];
        for (var i = 0; i < list.length; i++) {
            //取出itemList的对象:{"spec":{"网络":"移动3G"},"price":0,"num":99999,"status":"0","isDefault":"0"}
            var oldRow = list[i];
            for (var j = 0; j < columnValues.length; j++) {
                var newRow = JSON.parse(JSON.stringify(oldRow));//深克隆
                //塞新的spec进去:{"spec":{"网络":"移动3G","机身内存":"32G"},"price":0,"num":99999,"status":"0","isDefault":"0"}
                newRow.spec[columnName] = columnValues[j];
                newList.push(newRow);
            }
        }
        return newList;
    }

});
