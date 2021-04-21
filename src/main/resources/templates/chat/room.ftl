<!doctype html>
<html lang="en">
<head>
    <title>Websocket Chat</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <!-- CSS -->
    <link rel="stylesheet" href="/webjars/bootstrap/4.3.1/dist/css/bootstrap.min.css">
    <style>
        [v-cloak] {
            display: none;
        }
    </style>
</head>
<body>
<div class="container" id="app" v-cloak>
    <div class="row">
        <div class="col-md-6">
            <h3>채팅방 리스트</h3>
        </div>
        <div class="col-md-6 text-right">
            <a class="btn btn-primary btn-sm" href="/logout">로그아웃</a>
        </div>
    </div>
    <div class="input-group">
        <div class="input-group-prepend">
            <label class="input-group-text">방제목</label>
        </div>
        <#--        <input type="text" class="form-control" v-model="room_name" v-on:keyup.enter="createRoom">-->
        <input type="text" class="form-control" v-model="roomName" v-on:keyup.enter="createRoom">
        <div class="input-group-append">
            <button class="btn btn-primary" type="button" @click="createRoom">채팅방 개설</button>
        </div>
    </div>
    <ul class="list-group">
        <#--        <li class="list-group-item list-group-item-action" v-for="item in chatrooms" v-bind:key="item.roomId" v-on:click="enterRoom(item.roomId, item.name)">-->
        <#--            <h6>{{item.name}} <span class="badge badge-info badge-pill">{{item.userCount}}</span></h6>-->
        <li class="list-group-item list-group-item-action" v-for="item in chatrooms" v-bind:key="item.roomId" v-on:click="enterRoom(item.roomId, item.roomName, item.userInterested)">
            <h6>{{item.roomName}} <span class="badge badge-info badge-pill">{{item.userCount}}</span></h6>
        </li>
    </ul>
</div>m
<!-- JavaScript -->
<script src="/webjars/vue/2.5.16/dist/vue.min.js"></script>
<script src="/webjars/axios/0.17.1/dist/axios.min.js"></script>
<script>
    var vm = new Vue({
        el: '#app',
        data: {
            // room_name : '',
            roomName: '',
            userInterested: '',
            chatrooms: []
        },
        created() {
            this.findAllRoom();
        },
        methods: {
            findAllRoom: function() {
                axios.get('/api/chat/rooms').then(response => {
                    // prevent html, allow json array
                    if(Object.prototype.toString.call(response.data) === "[object Array]")
                        this.chatrooms = response.data;
                });
            },
            //방 개설시 넘겨줄 데이터는?? roomName, userInterested
            createRoom: function() {
                // const headers = {
                //     'Content-Type': 'application/json; charset=utf-8'
                // }
                // if("" === this.room_name) {
                if("" === this.roomName) {
                    alert("방 제목을 입력해 주십시요.");
                    return;
                } else {
                    // let params = new URLSearchParams();
                    var params = new URLSearchParams();

                    alert(params)
                    // params.append("name",this.room_name);
                    // params.append("name",this.roomName);
                    params.append("roomName",this.roomName);
                    // params.append("userInterested", this.userInterested)
                    alert(params)

                    // const config = {
                    //         'Content-Type': 'application/json, charset=utf-8'
                    // }
                    axios.post('/api/chat/room', params, {
                    })//415 에러 해결 시도(여전히 뜸)
                        // axios.post('/api/chat/room', JSON.stringify({roomName:this.roomName}), {})

                        .then(
                            response => {
                                // alert(response.data.name+"방 개설에 성공하였습니다.")
                                alert(response.data.roomName+"방 개설에 성공하였습니다.")
                                // this.room_name = '';
                                this.roomName = '';
                                this.findAllRoom();
                            }
                        )
                        .catch( response => { alert("채팅방 개설에 실패하였습니다."); } );
                }
            },
            enterRoom: function(roomId, roomName, userInterested) {
                localStorage.setItem('wschat.roomId',roomId);
                localStorage.setItem('wschat.roomName',roomName);
                localStorage.setItem('wschat.userInterested',userInterested);
                // location.href="/api/chat/room/enter/"+roomId;
                location.href="/api/chat/room/"+roomId;
            }
        }
    });
</script>
</body>
</html>