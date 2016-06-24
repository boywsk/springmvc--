var TreeDeptData = { Rows : [
        { id: '01', name: "企划部",   remark: "1989-01-12",
            children: [
	            { id: '0101', name: "企划分部一", remark: "企划分部一"
	            },
	            { id: '0102', name: "企划分部二", remark: "企划分部二"
	            },
	            { id: '0103', name: "企划分部三", remark: "企划分部三" }
            ]
        },
        { id: '02', name: "研发部", remark: "研发部" },
        { id: '03', name: "产品部", remark: "产品部" }  
    ]
};

var TreeDeptData2 = {
	Rows : [ {
		id : '01',
		name : "企划部",
		remark : "1989-01-12",
		children : [ {
			id : '0101',
			name : "企划分部一",
			remark : "企划分部一"
		}, {
			id : '0102',
			name : "企划分部二",
			remark : "企划分部二"
		}, {
			id : '0103',
			name : "企划分部三",
			remark : "企划分部三"
		} ]
	}, {
		id : '02',
		name : "研发部",
		remark : "研发部"
	}, {
		id : '03',
		name : "产品部",
		remark : "产品部"
	} ]
};


var TreeDeptData3 = {
    "Rows": [
        {
            "children": [
                {
                    "id": 2,
                    "name": "文件上传",
                    "orderBy": 1,
                    "pid": 1,
                    "url": "../upload.jsp"
                },
                {
                    "id": 3,
                    "name": "文件列表",
                    "orderBy": 2,
                    "pid": 1,
                    "url": "../file/listUserFiles.do"
                },
                {
                    "id": 4,
                    "name": "文件统计",
                    "orderBy": 3,
                    "pid": 1,
                    "url": "../file/preStatistics.do"
                },
                {
                    "id": 5,
                    "name": "头像上传",
                    "orderBy": 4,
                    "pid": 1,
                    "url": "../upload_avatar.jsp"
                },
                {
                    "id": 6,
                    "name": "头像列表",
                    "orderBy": 5,
                    "pid": 1,
                    "url": "../avatar/listAvatar.do"
                },
                {
                    "id": 7,
                    "name": "头像统计",
                    "orderBy": 6,
                    "pid": 1,
                    "url": "../avatar/preStatistics.do"
                },
                {
                    "id": 8,
                    "name": "旧头像列表",
                    "orderBy": 7,
                    "pid": 1,
                    "url": "../avatar/listAvatarOld.do"
                },
                {
                    "id": 9,
                    "name": "旧头像统计",
                    "orderBy": 8,
                    "pid": 1,
                    "url": "../avatar/preStatisticsOld.do"
                },
                {
                    "id": 10,
                    "name": "娱乐文件列表",
                    "orderBy": 9,
                    "pid": 1,
                    "url": "../forum/listForum.do"
                }
            ],
            "id": 1,
            "name": "文件管理",
            "orderBy": 1,
            "pid": 0,
            "url": ""
        },
        {
            "children": [
                {
                    "id": 21,
                    "name": "文件管理",
                    "orderBy": 1,
                    "pid": 20,
                    "url": "www.baidu.com"
                }
            ],
            "id": 20,
            "name": "离线文件管理",
            "orderBy": 2,
            "pid": 0,
            "url": ""
        },
        {
            "children": [
                {
                    "id": 203,
                    "name": "修改密码",
                    "orderBy": 1,
                    "pid": 200,
                    "url": "../user/preEditPassword.do"
                },
                {
                    "id": 201,
                    "name": "用户管理",
                    "orderBy": 2,
                    "pid": 200,
                    "url": "../user/listUser.do"
                },
                {
                    "id": 202,
                    "name": "菜单管理",
                    "orderBy": 3,
                    "pid": 200,
                    "url": "../menu/listMenu.do"
                }
            ],
            "id": 200,
            "name": "系统管理",
            "orderBy": 20,
            "pid": 0,
            "url": ""
        }
    ]
}