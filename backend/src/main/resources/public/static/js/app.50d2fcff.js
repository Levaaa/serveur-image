(function(e){function t(t){for(var c,o,l=t[0],i=t[1],u=t[2],s=0,b=[];s<l.length;s++)o=l[s],Object.prototype.hasOwnProperty.call(a,o)&&a[o]&&b.push(a[o][0]),a[o]=0;for(c in i)Object.prototype.hasOwnProperty.call(i,c)&&(e[c]=i[c]);d&&d(t);while(b.length)b.shift()();return r.push.apply(r,u||[]),n()}function n(){for(var e,t=0;t<r.length;t++){for(var n=r[t],c=!0,o=1;o<n.length;o++){var i=n[o];0!==a[i]&&(c=!1)}c&&(r.splice(t--,1),e=l(l.s=n[0]))}return e}var c={},a={app:0},r=[];function o(e){return l.p+"static/js/"+({about:"about"}[e]||e)+"."+{about:"83407444"}[e]+".js"}function l(t){if(c[t])return c[t].exports;var n=c[t]={i:t,l:!1,exports:{}};return e[t].call(n.exports,n,n.exports,l),n.l=!0,n.exports}l.e=function(e){var t=[],n=a[e];if(0!==n)if(n)t.push(n[2]);else{var c=new Promise((function(t,c){n=a[e]=[t,c]}));t.push(n[2]=c);var r,i=document.createElement("script");i.charset="utf-8",i.timeout=120,l.nc&&i.setAttribute("nonce",l.nc),i.src=o(e);var u=new Error;r=function(t){i.onerror=i.onload=null,clearTimeout(s);var n=a[e];if(0!==n){if(n){var c=t&&("load"===t.type?"missing":t.type),r=t&&t.target&&t.target.src;u.message="Loading chunk "+e+" failed.\n("+c+": "+r+")",u.name="ChunkLoadError",u.type=c,u.request=r,n[1](u)}a[e]=void 0}};var s=setTimeout((function(){r({type:"timeout",target:i})}),12e4);i.onerror=i.onload=r,document.head.appendChild(i)}return Promise.all(t)},l.m=e,l.c=c,l.d=function(e,t,n){l.o(e,t)||Object.defineProperty(e,t,{enumerable:!0,get:n})},l.r=function(e){"undefined"!==typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(e,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(e,"__esModule",{value:!0})},l.t=function(e,t){if(1&t&&(e=l(e)),8&t)return e;if(4&t&&"object"===typeof e&&e&&e.__esModule)return e;var n=Object.create(null);if(l.r(n),Object.defineProperty(n,"default",{enumerable:!0,value:e}),2&t&&"string"!=typeof e)for(var c in e)l.d(n,c,function(t){return e[t]}.bind(null,c));return n},l.n=function(e){var t=e&&e.__esModule?function(){return e["default"]}:function(){return e};return l.d(t,"a",t),t},l.o=function(e,t){return Object.prototype.hasOwnProperty.call(e,t)},l.p="/",l.oe=function(e){throw console.error(e),e};var i=window["webpackJsonp"]=window["webpackJsonp"]||[],u=i.push.bind(i);i.push=t,i=i.slice();for(var s=0;s<i.length;s++)t(i[s]);var d=u;r.push([0,"chunk-vendors"]),n()})({0:function(e,t,n){e.exports=n("56d7")},"0d8a":function(e,t,n){},"11c1":function(e,t,n){},"4b1e":function(e,t,n){},"56d7":function(e,t,n){"use strict";n.r(t);n("e260"),n("e6cf"),n("cca6"),n("a79d");var c=n("7a23"),a={id:"nav"},r=Object(c["f"])("Home"),o=Object(c["f"])(" | "),l=Object(c["f"])("About"),i=Object(c["f"])(" | "),u=Object(c["f"])("Edit");function s(e,t){var n=Object(c["w"])("router-link"),s=Object(c["w"])("router-view");return Object(c["p"])(),Object(c["d"])(c["a"],null,[Object(c["g"])("div",a,[Object(c["g"])(n,{to:"/"},{default:Object(c["D"])((function(){return[r]})),_:1}),o,Object(c["g"])(n,{to:"/about"},{default:Object(c["D"])((function(){return[l]})),_:1}),i,Object(c["g"])(n,{to:"/edit"},{default:Object(c["D"])((function(){return[u]})),_:1})]),Object(c["g"])(s)],64)}n("e8a4");const d={};d.render=s;var b=d,f=(n("d3b7"),n("3ca3"),n("ddb0"),n("6c02")),m=n("cf05"),p=n.n(m),g={class:"home"},O=Object(c["g"])("img",{alt:"Vue logo",src:p.a},null,-1);function j(e,t,n,a,r,o){var l=Object(c["w"])("HelloWorld");return Object(c["p"])(),Object(c["d"])("div",g,[O,Object(c["g"])(l,{msg:"Welcome to Your Vue.js App"})])}n("b0c0");var h=Object(c["F"])("data-v-32e73199");Object(c["s"])("data-v-32e73199");var v={class:"hello"},y=Object(c["g"])("button",{class:"downloadButton"},"Download",-1),w={class:"large-12 medium-12 small-12 cell"},S=Object(c["g"])("br",null,null,-1),k=Object(c["g"])("br",null,null,-1),x=Object(c["g"])("img",{class:"imgDisplay"},null,-1),E=Object(c["g"])("br",null,null,-1),_=Object(c["g"])("br",null,null,-1),A=Object(c["g"])("br",null,null,-1),R={key:0,class:"grid-container"},U={class:"meta"},C={class:"tooltip"};Object(c["q"])();var I=h((function(e,t,n,a,r,o){var l=Object(c["w"])("router-link");return Object(c["p"])(),Object(c["d"])("div",v,[Object(c["g"])("h1",null,Object(c["y"])(e.msg),1),Object(c["g"])("button",{class:"Search__button",onClick:t[1]||(t[1]=function(t){return e.callRestService()})},"Call Spring Boot REST backend"),Object(c["E"])(Object(c["g"])("select",{"onUpdate:modelValue":t[2]||(t[2]=function(t){return e.selected=t})},[(Object(c["p"])(!0),Object(c["d"])(c["a"],null,Object(c["v"])(e.response,(function(e){return Object(c["p"])(),Object(c["d"])("option",{value:{name:e.name,id:e.id},key:e.id},Object(c["y"])(e.name),9,["value"])})),128))],512),[[c["A"],e.selected]]),Object(c["g"])("button",{class:"Search__button",onClick:t[3]||(t[3]=function(t){return e.getImage(e.selected)})},"Get image"),Object(c["g"])("a",{href:e.getUrl(e.selected),download:e.selected.name},[y],8,["href","download"]),Object(c["g"])("button",{onFormmethod:t[4]||(t[4]=function(){return e.DELETE&&e.DELETE.apply(e,arguments)}),onClick:t[5]||(t[5]=function(t){e.deleteImage(e.selected),e.callRestService()})},"Delete",32),Object(c["g"])("div",w,[Object(c["g"])("label",null,[Object(c["g"])("input",{type:"file",id:"file",ref:"file",onChange:t[6]||(t[6]=function(t){return e.handleFileUpload()})},null,544)]),Object(c["g"])("button",{onClick:t[7]||(t[7]=function(t){e.submitFile(),e.callRestService()})},"Submit")]),S,k,x,E,Object(c["g"])("button",{onClick:t[8]||(t[8]=function(t){e.show=!e.show}),class:"Search__button"},"Display Gallery"),_,A,e.show?(Object(c["p"])(),Object(c["d"])("div",R,[(Object(c["p"])(!0),Object(c["d"])(c["a"],null,Object(c["v"])(e.response,(function(t){return Object(c["p"])(),Object(c["d"])("div",{key:t.id},[Object(c["g"])("div",U,[Object(c["g"])(l,{to:{path:"/edit",query:{id:t.id}}},{default:h((function(){return[Object(c["g"])("img",{class:"div-img",src:"/images/"+t.id,alt:t.name},null,8,["src","alt"])]})),_:2},1032,["to"]),Object(c["g"])("span",C,Object(c["y"])(e.meta[t.id]),1)])])})),128))])):Object(c["e"])("",!0)])})),D=n("bc3a"),F=n.n(D),M={name:"HelloWorld",props:{msg:String},data:function(){return{show:!1,response:[],errors:[],file:"",images:[],meta:[],selected:{name:null,id:0}}},mounted:function(){this.callRestService(),this.getMeta()},methods:{callRestService:function(){var e=this;F.a.get("images/get").then((function(t){e.response=t.data,e.selected.name=t.data[0].name})).catch((function(t){e.errors.push(t)})),this.addImageMeta()},addImageMeta:function(){var e=this,t=this.response.length,n="/images/"+t+"/get";F.a.get(n).then((function(n){e.meta[t]=n.data})).catch((function(t){e.errors.push(t)}))},getMeta:function(){var e=this;F.a.get("images").then((function(t){e.meta=t.data})).catch((function(t){e.errors.push(t)}))},getImage:function(e){var t="/images/"+e.id,n=document.querySelector(".imgDisplay");F.a.get(t,{responseType:"blob"}).then((function(e){var t=new window.FileReader;t.readAsDataURL(e.data),t.onload=function(){var e=t.result;n.setAttribute("src",e)}}))},getUrl:function(e){return"/images/"+e.id},handleFileUpload:function(){this.file=this.$refs.file.files[0]},submitFile:function(){var e=new FormData;e.append("file",this.file),F.a.post("/images",e,{headers:{"Content-Type":"multipart/file"}}).then((function(){console.log("SUCCESS!!")})).catch((function(e){console.log(e),console.log("FAILURE!!")}))},deleteImage:function(e){var t="/images/"+e.id;F.a.delete(t)}}};n("7895");M.render=I,M.__scopeId="data-v-32e73199";var T=M,q={name:"Home",components:{HelloWorld:T}};q.render=j;var P=q,L={class:"edit"};function V(e,t,n,a,r,o){var l=Object(c["w"])("Modif");return Object(c["p"])(),Object(c["d"])("div",L,[Object(c["g"])(l)])}var H=Object(c["F"])("data-v-11135e51");Object(c["s"])("data-v-11135e51");var W={class:"edit"},B={class:"imageResult"},G={class:"tools"},$=Object(c["g"])("h1",null,"TOOLS",-1),z={key:0},J=Object(c["g"])("br",null,null,-1),Y={key:1},K={key:0,style:{color:"red"}},N=Object(c["f"])(" Selectionnez votre effet ! "),Q=Object(c["g"])("br",null,null,-1),X=Object(c["g"])("br",null,null,-1),Z=Object(c["g"])("br",null,null,-1),ee=Object(c["g"])("br",null,null,-1),te=Object(c["g"])("input",{type:"button",class:"button",value:"download"},null,-1),ne=Object(c["g"])("input",{type:"button",class:"button",value:"reset"},null,-1),ce=Object(c["g"])("br",null,null,-1);Object(c["q"])();var ae=H((function(e,t,n,a,r,o){return Object(c["p"])(),Object(c["d"])("div",W,[Object(c["g"])("div",B,[Object(c["g"])("img",{class:"photo",src:o.getUrl(r.selected)},null,8,["src"]),Object(c["g"])("img",{class:"initialPhoto",src:o.getUrl(r.selected),onClick:t[1]||(t[1]=function(e){return o.exchangeImages()})},null,8,["src"])]),Object(c["g"])("div",G,[$,Object(c["g"])("h2",null,Object(c["y"])(r.effect.name),1),"brightness"===r.effect.name||"coloration"===r.effect.name||"meanFilter"===r.effect.name?(Object(c["p"])(),Object(c["d"])("div",z,[Object(c["E"])(Object(c["g"])("input",{type:"range",min:r.effect.min,max:r.effect.max,step:r.effect.step,"onUpdate:modelValue":t[2]||(t[2]=function(t){return e.value=t}),class:"slider",onChange:t[3]||(t[3]=function(e){return o.applyEffect(r.selected,r.effect)}),onInput:t[4]||(t[4]=function(t){return r.effect.param=e.value})},null,40,["min","max","step"]),[[c["B"],e.value]]),J,Object(c["f"])(" "+Object(c["y"])(r.effect.param),1)])):(Object(c["p"])(),Object(c["d"])("div",Y,[r.errorMessage?(Object(c["p"])(),Object(c["d"])("b",K,[N,Q,X])):Object(c["e"])("",!0),Object(c["g"])("button",{class:"Search__button",onClick:t[5]||(t[5]=function(e){o.applyEffect(r.selected,r.effect),o.callRestService()})},"Apply effect")])),Z,ee]),Object(c["g"])("div",null,[Object(c["g"])("input",{type:"button",class:"button",value:"save",onClick:t[6]||(t[6]=function(t){return e.getId()})}),te,ne,Object(c["E"])(Object(c["g"])("select",{name:"selecteurImage",class:"button","onUpdate:modelValue":t[7]||(t[7]=function(e){return r.selected=e})},[(Object(c["p"])(!0),Object(c["d"])(c["a"],null,Object(c["v"])(r.response,(function(e){return Object(c["p"])(),Object(c["d"])("option",{value:{name:e.name,id:e.id},key:e.id},Object(c["y"])(e.name),9,["value"])})),128))],512),[[c["A"],r.selected]]),Object(c["E"])(Object(c["g"])("select",{name:"selecteurEffet",class:"button","onUpdate:modelValue":t[8]||(t[8]=function(e){return r.effect=e})},[(Object(c["p"])(!0),Object(c["d"])(c["a"],null,Object(c["v"])(r.name,(function(e){return Object(c["p"])(),Object(c["d"])("option",{value:{name:e.name,param:e.param,min:e.min,max:e.max,step:e.step},key:e},Object(c["y"])(e.name),9,["value"])})),128))],512),[[c["A"],r.effect]])]),ce])})),re={name:"Edit",data:function(){return{show:!1,response:[],errors:[],file:"",images:[],exchanged:!1,errorMessage:!1,id:this.$route.query.id,selected:{name:null,id:0},effect:{name:null,param:null,min:null,max:null,step:null},name:[{name:"contrast"},{name:"brightness",param:0,min:-255,max:255,step:1},{name:"equalizer"},{name:"toGrey"},{name:"coloration",param:180,min:0,max:360,step:1},{name:"meanFilter",param:7,min:1,max:15,step:2},{name:"edges"}]}},mounted:function(){this.callRestService()},methods:{callRestService:function(){var e=this;F.a.get("images/get").then((function(t){e.response=t.data,void 0!==e.id?(e.selected.name=t.data[e.id].name,e.selected.id=e.id):e.selected.name=t.data[0].name})).catch((function(t){e.errors.push(t)}))},getImage:function(e){var t="/images/"+e.id,n=document.querySelector(".imgDisplay");F.a.get(t,{responseType:"blob"}).then((function(e){var t=new window.FileReader;t.readAsDataURL(e.data),t.onload=function(){var e=t.result;n.setAttribute("src",e)}}))},getUrl:function(e){return"/images/"+e.id},handleFileUpload:function(){this.file=this.$refs.file.files[0]},getValue:function(e){console.log(e)},applyEffect:function(e,t){if(null!=t.name){this.errorMessage=!1,1==this.exchanged&&this.exchangeImages();var n="/images/"+e.id+"?algorithm="+t.name;null!=t.param&&(n+="&p1="+t.param);var c=document.querySelector(".photo");F.a.get(n,{responseType:"blob"}).then((function(e){var t=new window.FileReader;t.readAsDataURL(e.data),t.onload=function(){var e=t.result;c.setAttribute("src",e)}}))}else this.errorMessage=!0},exchangeImages:function(){var e=document.querySelector(".photo"),t=document.querySelector(".initialPhoto"),n=e.getAttribute("src"),c=t.getAttribute("src");e.setAttribute("src",c),t.setAttribute("src",n),this.exchanged=!this.exchanged}}};n("edab");re.render=ae,re.__scopeId="data-v-11135e51";var oe=re,le={name:"Edit",components:{Modif:oe}};le.render=V;var ie=le,ue=[{path:"/",name:"Home",component:P},{path:"/about",name:"About",component:function(){return n.e("about").then(n.bind(null,"f820"))}},{path:"/edit",name:"edit",component:ie}],se=Object(f["a"])({history:Object(f["b"])("/"),routes:ue}),de=se;Object(c["c"])(b).use(de).mount("#app")},7895:function(e,t,n){"use strict";n("4b1e")},cf05:function(e,t,n){e.exports=n.p+"static/img/logo.82b9c7a5.png"},e8a4:function(e,t,n){"use strict";n("11c1")},edab:function(e,t,n){"use strict";n("0d8a")}});
//# sourceMappingURL=app.50d2fcff.js.map