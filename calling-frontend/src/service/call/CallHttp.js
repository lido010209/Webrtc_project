import axios from "axios";

class CallHttp{
  deleteCall(token, callId){
    return axios.delete(`/call/${callId}`, {
      headers: {
        "Authorization": `Bearer ${token}`,
      }
    })
  }

  checkAvailable(token, friendId){
    return axios.get(`/call/check-available/${friendId}`, {
      headers: {
        "Authorization": `Bearer ${token}`,
      }
    })
  }
}

export default new CallHttp();