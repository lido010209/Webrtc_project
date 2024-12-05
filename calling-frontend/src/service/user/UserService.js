import axios from 'axios'

class UserService{
    newUser(request){
        return axios.post('/users', request);
    }

    getToken(request){
        return axios.post('/token', request)
    }

    userLogin(token){
        return axios.get('/users', {
            headers:{
                "Authorization": `Bearer ${token}`
            }
        })
    }
}

export default new UserService()