import axios from 'axios'

class UserService{
    newUser(request){
        return axios.post('/users', request);
    }
}

export default new UserService()