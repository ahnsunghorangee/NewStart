import Vue from 'vue'
import Router from 'vue-router'
import Home from './views/Home.vue'
import ToHandLan from './views/HandLan/ToHandLan.vue'
import FromHandLan from './views/HandLan/FromHandLan.vue'
import Board from './views/board/Board.vue'
import BoardCreate from './views/board/BoardCreate.vue'
import BoardDetail from './views/board/BoardDetail.vue'
import BoardUpdate from './views/board/BoardUpdate.vue'
import Camera from './components/Camera.vue'
import FromHandLanResult from './views/HandLan/FromHandLanResult.vue'
import ToHandLanResult from './views/HandLan/ToHandLanResult.vue'
import FromHandLanSend from './views/HandLan/FromHandLanSend.vue'
import Video from './components/Video.vue'
import ToHandLanChoice from './views/HandLan/ToHandLanChoice.vue'
import ToHandLanDic from './views/HandLan/ToHandLanDic.vue'
import Upload from './views/Upload.vue'
import MyNote from './views/myNote/myNote.vue'
import MyPhoneBook from './views/myNote/myPhoneBook.vue'

Vue.use(Router) //플러그인 등록

export default new Router({
    mode:'history',
    routes: [
        {
            path: '/',
            name: 'Home',
            component: Home,
        },
        {
            path: '/toHandLan',
            name: 'ToHandLan',
            component: ToHandLan,
        },
        {
            path: '/fromHandLan',
            name: 'FromHandLan',
            component: FromHandLan,
        },
        {
            path: '/board',
            name: 'Board',
            component: Board,
        },
        {
            path: '/board/create',
            name: 'BoardCreate',
            component: BoardCreate,
        },
        {
            path: '/board/detail/:id',
            name: 'BoardDetail',
            component: BoardDetail,
        },
        {
            path: '/board/update/:id',
            name: 'BoardUpdate',
            component: BoardUpdate,
        },
        {
            path: '/camera',
            name: 'Camera',
            component: Camera,
        },
        {
            path: '/fromHandLanResult',
            name: 'FromHandLanResult',
            component: FromHandLanResult,
        },
        {
            path: '/toHandLanResult',
            name: 'ToHandLanResult',
            component: ToHandLanResult,
        },
        {
            path: '/fromHandLanSend',
            name: 'FromHandLanSend',
            component: FromHandLanSend,
        },
        {
            path: '/video',
            name: 'Video',
            component: Video,
        },
        {
            path:'/toHandLanChoice',
            name: 'ToHandLanChoice',
            component : ToHandLanChoice
        },
        {
            path:'/toHandLanDic',
            name: 'ToHandLanDic',
            component : ToHandLanDic
        }, 
        {
            path: '/upload',
            name: 'Upload',
            component: Upload,
        },
        {
            path:'/myNote',
            name: 'MyNote',
            component : MyNote
        },
        {
            path : '/myPhoneBook',
            name : 'MyPhoneBook',
            component : MyPhoneBook
        }
    ]
})