var Detection = Ractive.extend({
  template: '#detection',
  rpcEchoString(echoString) {
    fetch('/consumer-echo/' + echoString)
      .then(res => res.text())
      .then(data => {
        var timestamp = /\d{13}/g ;
        data = data.replace(timestamp, (b, c) =>
          b == 'undefined' ? '' : new Date(+b).toISOString() + " : "
        )
        this.set('respondedString', data)
      })
  },
  componentDidMount() {
    fetch('/ping')
      .then(res => res.json())
      .then(data => this.set('serviceExisted', data))
      .finally(data => this.set('loading', false))
  }
})

var detection = new Detection({
  target: '.detection-result',
  data: {
    loading: true,
    serviceExisted: false,
    echoString: 'Echo this string',
    respondedString: ''
  }
})
detection.componentDidMount()
