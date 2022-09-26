package playground

class Curl {

    static void main(String[] args) {
        getStatus('https://httpstat.us/200')
        getStatus('https://httpstat.us/400')
    }

    static getStatus(String url) {
        Process curl = [
                'curl', '--insecure', '--silent', '--show-error', '--fail-with-body',
//                '--write-out', ' %{http_code}',
                '--header', 'Accept:application/json',
                url
        ].execute()
        int exitValue = curl.waitFor()
        if (exitValue == 0) {
            println "Curl Success: ${curl.text}"
        } else {
            println "Curl Failure: ${url}\n${curl.errorStream.text}${curl.text}"
        }
    }

}
