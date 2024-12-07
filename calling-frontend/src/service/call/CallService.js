class CallService {
    // client() {
    //     return new Client({
    //         brokerURL: "ws://localhost:8081/ws",
    //     })
    // }

    signalizing(client, request){
        return client.publish({
            destination: '/app/signaling',
            body: JSON.stringify(request)
        })
    }

    onCall(client, request){
        return client.publish({
            destination: "/app/on-call",
            body: JSON.stringify(request),
        })
    }

    reject(client, request){
        return client.publish({
            destination: "/app/reject",
            body: JSON.stringify(request),
        })
    }

    missCall(client, request){
        return client.publish({
            destination: "/app/miss-call",
            body: JSON.stringify(request),
        })
    }

    hangup(client, request){
        return client.publish({
            destination: "/app/hangup",
            body: JSON.stringify(request),
        })
    }

    makeOffer(client, offer){
        return client.publish({
            destination: "/app/offer",
            body: JSON.stringify(offer)
        })
    }

    makeAnswer(client, answer){
        return client.publish({
            destination: "/app/answer",
            body: JSON.stringify(answer),
        })
    }

    makeCandidate(client, candidate){
        return client.publish({
            destination: "/app/candidate",
            body: JSON.stringify(candidate),
        })
    }
}

export default new CallService();