<template>
  <div>
    <Navbar />
  </div>
  <div class="p-3 d-flex">
    <div class="container-fluid d-flex flex-wrap col-12 col-lg-9">
      <a
        href="#"
        @click.prevent="callFriend(friend.id)"
        class="p-2 col-12 col-sm-6 col-lg-4 text-decoration-none"
        v-for="friend in friends"
        :key="friend.id"
      >
        <div class="border rounded p-3">
          <div class="d-flex justify-content-between w-100">
            <img src="@/assets/calling/frame1.png" class="img-fluid col-3" />
            <img src="@/assets/calling/frame2.png" class="img-fluid col-3" />
          </div>
          <div class="d-flex justify-content-center flex-wrap">
            <div class="w-100 text-center mb-3">
              <img :src="friend.avatar" class="img-fluid w-25" />
            </div>
            <h3 class="fw-bold">{{ friend.name }}</h3>
          </div>
          <div class="d-flex justify-content-between">
            <img src="@/assets/calling/frame3.png" class="img-fluid col-3" />
            <img src="@/assets/calling/frame4.png" class="img-fluid col-3" />
          </div>
        </div>
      </a>
    </div>
    <div class="d-none d-lg-block col-3 p-3 text-end">
      <img src="@/assets/calling/friend.png" class="img-fluid sticky" />
    </div>
  </div>
  <div
    class="ms-5 me-5 mt-3 border border-4 rounded bg-light fixed-top"
    :class="{ 'd-none': isCall }"
  >
    <div
      class="d-flex justify-content-center ps-3 pe-3 text-white align-items-center"
      style="background-color: darkgreen"
    >
      <h3>통화중</h3>
    </div>
    <div class="p-3 d-flex flex-wrap">
      <div class="d-flex w-100 justify-content-center p-3 gap-3">
        <div
          :class="{ 'd-none': remoteDisplay }"
          class="col-12 col-lg-6 border bg-white rounded"
          style="height: 400px"
        >
          <video
            ref="remoteVideo"
            style="transform: scaleX(-1)"
            class="h-100 w-100 rounded"
            autoplay
            playsinline
          ></video>
        </div>
        <div
          :class="localDisplay"
          class="border bg-white rounded"
          style="height: 400px"
        >
          <video
            ref="localVideo"
            style="transform: scaleX(-1)"
            class="h-100 w-100 rounded"
            autoplay
            playsinline
          ></video>
        </div>
      </div>
      <div class="w-100 d-flex justify-content-center">
        <button class="btn btn-outline-primary me-3" @click.prevent="muteVideo">
          <i class="fa-solid" :class="{'fa-video': isVideo, 'fa-video-slash': !isVideo}"></i>
        </button>
        <button class="btn btn-outline-primary me-3" @click.prevent="muteAudio">
          <i class="fa-solid" :class="{'fa-microphone': isAudio, 'fa-microphone-slash': !isAudio}"></i>
        </button>
        <button class="btn btn-outline-danger" @click.prevent="hangup">
          <i class="fa-solid fa-phone-flip"></i>
        </button>
      </div>
    </div>
  </div>
  <div
    :class="{ 'd-none': isBell }"
    class="mt-5 d-flex justify-content-center fixed-top"
  >
    <div
      class="col-9 col-md-6 col-lg-4 d-flex border rounded border-4 flex-wrap shadow-md bg-white"
    >
      <div
        class="d-flex justify-content-center text-white w-100"
        style="background-color: darkgreen"
      >
        <h4>전화</h4>
      </div>
      <div class="d-flex justify-content-center flex-wrap">
        <div class="w-100 d-flex justify-content-center p-5">
          <img
            :src="callerImg"
            class="img-fluid"
            style="height: 150px; width: 150px"
          />
        </div>
        <div class="text-center fs-4 fw-bold w-100 mb-3 ps-5 pe-5">
          {{ callerName }}
        </div>
        <div class="mb-5">
          <button @click.prevent="accept" class="btn btn-success me-3">
            <i class="fa-solid fa-phone-flip"></i> 통화
          </button>
          <button @click.prevent="reject" class="btn btn-danger">
            <i class="fa-solid fa-phone-slash"></i> 거절
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Navbar from "@/components/navbar/Navbar.vue";
import UserService from "@/service/user/UserService";
import CallService from "@/service/call/CallService";
import CallHttp from "@/service/call/CallHttp";
import { Client } from "@stomp/stompjs";
export default {
  name,
  components: {
    Navbar,
  },
  data() {
    return {
      token: localStorage.getItem("token"),
      userLogin: null,
      friends: [],
      isCall: true,
      isBell: true,
      configuration: {
        iceServers: [
          {
            urls: [
              "stun:stun.l.google.com:19302",
              "stun:stun1.l.google.com:19302",
              "stun:stun2.l.google.com:19302",
            ],
          },
        ],
      },
      client: new Client({
        brokerURL: "ws://localhost:8080/ws",
      }),
      localDisplay: {
        "col-12": true,
        "col-lg-9": true,
        "d-none": false,
        "d-lg-flex": false,
        "col-6": false,
      },
      remoteDisplay: true,
      localStream: null,
      pcCaller: null,
      pcCallee: null,
      nowCall: null,
      offerRes: null,
      iceCandidates: [],
      callerImg: require("@/assets/user/user.png"),
      callerName: null,
      isAudio: true,
      isVideo: true,
    };
  },
  methods: {
    allFriends() {
      UserService.allFriends(this.token).then((res) => {
        this.friends = res.data;
      });
    },
    login() {
      UserService.userLogin(this.token).then((res) => {
        this.userLogin = res.data;
      });
    },
    stompClient() {
      this.client.activate();
      this.client.onConnect = () => {
        this.client.subscribe("/topic/signaling", (res) => {
          const calling = JSON.parse(res.body);
          if (
            calling.callerId === this.userLogin.id ||
            calling.calleeId === this.userLogin.id
          ) {
            this.nowCall = calling;
            if (this.localStream) this.startCall();
            if (calling.calleeId === this.userLogin.id) {
              this.isBell = false;
              UserService.friend(this.token, calling.callerId).then((res) => {
                this.callerImg = res.data.avatar;
                this.callerName = res.data.name;
              });
            }
          }
        });

        this.client.subscribe("/topic/on-call", (res) => {
          const calling = JSON.parse(res.body);
          
          if (calling.callerId===this.userLogin.id || calling.calleeId === this.userLogin.id) {
            this.nowCall= calling;
            if (calling.calleeId === this.userLogin.id) this.receiveCall();
          }
        });

        this.client.subscribe("/topic/reject", (res) => {
          const calling = JSON.parse(res.body);
          if (calling.callerId === this.userLogin.id) {
            if (confirm("거절되었습니다!!!")) location.reload();
          }
        });

        this.client.subscribe("/topic/miss-call", res=>{
          const calling = JSON.parse(res.body);
          if (calling.calleeId===this.userLogin.id || calling.callerId ===this.userLogin.id) {
            if (calling.calleeId===this.userLogin.id && confirm('전화를 놓쳤습니다!!!')) location.reload();
          } 
        })

        this.client.subscribe("/topic/hangup", res=>{
          const calling = JSON.parse(res.body);
          if (calling.calleeId===this.userLogin.id || calling.callerId ===this.userLogin.id) {
            this.isCall=true;
            if (confirm('저의 서비스를 사랑해주셔서 감사합니다!!!')) location.reload();
          } 
        })

        this.client.subscribe("/topic/offer", (res) => {
          if (
            this.nowCall.callerId === this.userLogin.id ||
            this.nowCall.calleeId === this.userLogin.id
          )
            this.offerRes = JSON.parse(res.body);
        });

        this.client.subscribe("/topic/candidate", (res) => {
          if (
            this.nowCall.callerId === this.userLogin.id ||
            this.nowCall.calleeId === this.userLogin.id
          )
            this.iceCandidates.push(JSON.parse(res.body));
        });

        this.client.subscribe("/topic/answer", (res) => {
          const answer = JSON.parse(res.body);
          if (
            this.nowCall.callerId === this.userLogin.id ||
            this.nowCall.calleeId === this.userLogin.id
          ) {
            if (this.pcCaller)
              this.pcCaller
                .setRemoteDescription(new RTCSessionDescription(answer))
                .then(() => {
                  this.iceCandidates.forEach((candidate) => {
                    this.pcCaller.addIceCandidate(
                      new RTCIceCandidate(candidate)
                    );
                  });
                });
            this.iceCandidates = [];
            this.remoteDisplay = false;
            this.localDisplay["col-12"] = false;
            this.localDisplay["col-lg-9"] = false;
            this.localDisplay["d-none"] = true;
            this.localDisplay["d-lg-flex"] = true;
            this.localDisplay["col-6"] = true;
          }
        });
      };
    },
    callFriend(friendId) {
      this.isCall = false;
      CallHttp.checkAvailable(this.token, friendId).then(res=>{
        if (!res){
          alert(`바쁜 중입니다. 통화 불가능합니다!!!`);
          return;
        }
        navigator.mediaDevices
        .getUserMedia({ video: true, audio: true })
        .then((stream) => {
          this.$refs.localVideo.srcObject = stream;
          this.localStream = stream;
          stream.getAudioTracks()[0].enabled = true;
          CallService.signalizing(this.client, {
            callerId: this.userLogin.id,
            calleeId: friendId,
          });
        });
      })
    },
    startCall() {
      this.pcCaller = new RTCPeerConnection(this.configuration);
      this.pcCaller.addStream(this.localStream);

      this.pcCaller.onaddstream = (e) => {
        this.$refs.remoteVideo.srcObject = e.stream;
      };

      this.pcCaller
        .createOffer()
        .then((offer) => {
          return this.pcCaller.setLocalDescription(offer);
        })
        .then(() => {
          CallService.makeOffer(this.client, this.pcCaller.localDescription);
        });

      this.pcCaller.onicecandidate = (event) => {
        if (event.candidate) {
          CallService.makeCandidate(this.client, event.candidate);
        }
      };
    },
    receiveCall() {
      this.pcCallee = new RTCPeerConnection(this.configuration);
      this.pcCallee.addStream(this.localStream);

      this.pcCallee.onaddstream = (e) => {
        this.$refs.remoteVideo.srcObject = e.stream;
      };

      this.pcCallee
        .setRemoteDescription(new RTCSessionDescription(this.offerRes))
        .then(() => {
          return this.pcCallee.createAnswer();
        })
        .then((answer) => {
          return this.pcCallee.setLocalDescription(answer);
        })
        .then(() => {
          CallService.makeAnswer(this.client, this.pcCallee.localDescription);
        })
        .then(() => {
          this.iceCandidates.forEach((candidate) => {
            this.pcCallee.addIceCandidate(new RTCIceCandidate(candidate));
          });
          this.iceCandidates = [];
        });

      this.pcCallee.onicecandidate = (e) => {
        if (e.candidate) {
          CallService.makeCandidate(this.client, e.candidate);
        }
      };
    },
    hangup() {
      this.isCall=true;
      if (this.nowCall.status==="Connecting"){
        CallService.missCall(this.client, {id: this.nowCall.id});
      }
      else if (this.nowCall.status ==="OnCall"){
        CallHttp.deleteCall(this.token, this.nowCall.id);
        CallService.hangup(this.client, {id: this.nowCall.id});
      } 
    },
    accept() {
      this.isBell = true;
      this.isCall = false;
      navigator.mediaDevices
        .getUserMedia({ video: true, audio: true })
        .then((stream) => {
          this.$refs.localVideo.srcObject = stream;
          this.localStream = stream;
          stream.getAudioTracks()[0].enabled = true;
          CallService.onCall(this.client, { id: this.nowCall.id });
        });
    },
    reject() {
      this.isBell = true;
      CallService.reject(this.client, { id: this.nowCall.id });
    },
    muteAudio(){
      this.isAudio=!this.isAudio;
      this.localStream.getAudioTracks()[0].enabled = this.isAudio;
    },
    muteVideo(){
      this.isVideo=!this.isVideo;
      this.localStream.getAudioTracks()[0].enabled = this.isVideo;
    }
  },
  created() {
    this.allFriends();
    this.login();
    this.stompClient();
  },
};
</script>
