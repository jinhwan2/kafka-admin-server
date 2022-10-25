class HttpResponse: 
    def success(self, data): 
        return {
            "data" : data
        }, 200

HttpResponse = HttpResponse()