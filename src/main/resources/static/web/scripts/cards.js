Vue.createApp({
    data() {
        return {
        dataClient: [],
        cards:[],
        client:{},
        firstName:"",
        lastName:"",
        email:"",
        idCuenta: "",
        stringUpper: "",
        tarjetasCredito: [],
        tarjetasDebito: [],
        stringCompleta: "",
        numeroTarjeta: "",
        fechaTarjeta: "",
        ultimaCards: [],
        ultimaCard: {},
        fechaActual: ""
        }
    },


    created(){
        axios.get(`http://localhost:8080/api/clients/current`)
        .then(result =>{
            this.client = result.data
            this.cards = result.data.cards
            this.tarjetasDebito = this.cards.filter(tarjeta => tarjeta.type =="DEBITO")
            this.tarjetasCredito = this.cards.filter(tarjeta => tarjeta.type =="CREDITO")
            }
        )
    },

    methods:{
        eliminarTarjeta(cardSeleccionada){
            Swal.fire({
                title: `Estas seguro de eliminar la tarjeta ${cardSeleccionada.type} ${cardSeleccionada.color} ?`,
                icon: 'warning',
                showDenyButton: true,
                confirmButtonText: 'Eliminar tarjeta',
                denyButtonText: `Cancelar`,
            }).then((result) => {
                
                if (result.isConfirmed) {
                    axios.patch('/api/cards',`id=${cardSeleccionada.id}`,{headers:{'content-type':'application/x-www-form-urlencoded'}})
                    .then(res=> {
                        Swal.fire( `Tarjeta ${cardSeleccionada.type} ${cardSeleccionada.color} Eliminada`, '', 'success')
                        .then(res=> window.location.href = "/web/cards.html", 5000)
                    }) 
                    .catch (error => {
                        Swal.fire(error.response.data, '', 'error')
                    })
                } else if (result.isDenied) {
                    Swal.fire('Tarjeta no eliminada', '', 'error')
                    .then(res=> window.location.href = "/web/cards.html", 5000)
                }
            })
        },

        primerLetraToUpper (string) {
            this.stringUpper = string.charAt(0).toUpperCase() + string.slice(1)
            return this.stringUpper
        },

        separaString (string) {
            this.numeroTarjeta = string.split("-").join(" ")
            return this.numeroTarjeta
        },

        normalizaFechaCard (string) {
            this.fechaTarjeta = string.slice(2,7)
            this.fechaTarjeta = this.fechaTarjeta.split("-").join("/")
            return this.fechaTarjeta
        },

        logoUt(){
            axios.post('/api/logout').then(response =>  window.location.href = "/web/index.html")
        },
        
    },
    computed : {
        vencimientoDeTarjetas(){
            this.fechaActual =  new Date ()
            this.fechaActual = this.fechaActual.toISOString()
            this.fechaActual = this.fechaActual.split('T')[0]
        },
        ultimaTarjeta(){
            this.ultimaCards = this.cards.sort(function(a,b){return b.id - a.id})
            this.ultimaCard = this.ultimaCards[0]
            if(this.ultimaCard.fromDate == this.fechaActual){
                this.ultimaCard = this.ultimaCard
            }else {
                this.ultimaCard = ""
            }
        },
        

    }


    }).mount('#app')