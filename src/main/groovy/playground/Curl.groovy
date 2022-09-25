package playground

class Curl {

    static void main(String[] args) {
        getStatus('https://httpstat.us/200')
        getStatus('https://httpstat.us/400')
    }

    static getStatus(String url) {
        Process curl = [
                'curl', '--insecure', '--fail-with-body',
//                '--write-out', ' %{http_code}',
                '--header', 'Accept:application/json',
                url
        ].execute()
        int exitValue = curl.waitFor()
        if (exitValue == 0) {
            println "Curl Success: ${curl.text}"
        } else {
            def errorMsg = curl.errorStream.text  // A bunch of useless metadata, ending with the HTTP error code.
            def matcher = errorMsg =~ /(?m)The requested URL returned error: (\d{3})/
            def errorCode = matcher ? matcher.group(1) : errorMsg
            println "Curl Failure calling url: $url \n$errorCode \n${curl.text}"
        }
    }

}
