package playground

import groovy.json.JsonSlurper

class Json {
    static void main(String... args) {
        def resp = '{"name":"sample","address":{"country":"IN","state":"TN","city":"Chennai"}}'
        println actValToGet(resp, 'address.state')
    }

    static actValToGet(String resp, String params){
        JsonSlurper slurper = new JsonSlurper()
        Map values = slurper.parseText(resp) as Map
        def keys = params.split(/\./)
        def output = values
        keys.each { output = output.get(it) }
        return output
    }

    static actValToGet2(String resp, String params){
        JsonSlurper slurper = new JsonSlurper()
        def values = slurper.parseText(resp)
        def keys = params.split(/\./)
        return keys.inject(values) { map, key -> map.get(key) }
    }

    static actValToGet3(String resp, String params){
        params.split(/\./).inject(new JsonSlurper().parseText(resp)) { map, key -> map[key] }
    }
}
