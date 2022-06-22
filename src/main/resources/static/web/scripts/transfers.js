Vue.createApp({
    data() {
        return {
        dataClient: [],
        cuentas:[],
        client:{},
        stringUpper: "",
        divCuentaDestino: [],
        propiaotercero: "propia",
        cuentaOrigen:"",
        cuentaDestino:"",
        montoAtransferir: "",
        descripcion:"",
        cuentasDestino: [],
        transacciones: [],
        infocuentaOrigen: {}
        }
    },


    created(){
        axios.get(`/api/clients/current`)
        .then(result =>{
            this.dataClient = result.data
            this.cuentas = result.data.accounts
            this.client = result.data
            }
        )
    },

    methods:{
        transferir(){
            axios.post('/api/transactions',`amount=${this.montoAtransferir}&description=${this.descripcion}&accountOrigen=${this.cuentaOrigen}&accountDestino=${this.cuentaDestino}`,{headers:{'content-type':'application/x-www-form-urlencoded'}})
            .then(response => {
                Swal.fire('Transacción realizada','','success')
                .then(res=> window.location.href = "/web/accounts.html", 5000)
            })
            .catch (error => {
                Swal.fire({
                    icon:'error',
                    text: error.response.data,
                    footer: '<a href="./create-cards.html">Volver a intentar</a>'
                })
                    
            })
            
        },

        logoUt(){
            axios.post('/api/logout').then(response =>  window.location.href = "/web/index.html")
        }

    },
    computed : {
        
        filtrarCuentasQueNoSeanDeOrigen (){
            this.cuentasDestino = []
            this.cuentasDestino.push(...this.cuentas.filter(cuenta => cuenta.number != this.cuentaOrigen))
        },

        ultimasTransacciones () {
            this.transacciones = []
            this.infocuentaOrigen = this.cuentas.filter(cuenta => cuenta.number == this.cuentaOrigen)
            this.transacciones.push(...this.infocuentaOrigen.flatMap(cuenta => cuenta.transactions))
            let transaccionesOrdenadas = []
            transaccionesOrdenadas = this.transacciones.filter(transaction => transaction.id).sort(function(a,b){return b.id - a.id})
            this.transacciones = transaccionesOrdenadas
            this.transacciones = this.transacciones.slice(0,5)
        },
        
        
    }




    }).mount('#app')
