(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-50ca0441"],{"13d5":function(e,t,a){"use strict";var r=a("23e7"),n=a("d58f").left,i=a("a640"),s=a("ae40"),o=i("reduce"),c=s("reduce",{1:0});r({target:"Array",proto:!0,forced:!o||!c},{reduce:function(e){return n(this,e,arguments.length,arguments.length>1?arguments[1]:void 0)}})},"55b3":function(e,t,a){"use strict";a("72d8")},"72d8":function(e,t,a){},"96d5":function(e,t,a){"use strict";a.r(t);var r=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:"page"},[a("Header"),e.headFlag?a("inner-header"):e._e(),a("div",{staticClass:"outline"},[e._m(0),a("div",{staticClass:"outline-amount"},[a("div",{staticClass:"outline-amount-left"},[a("p",{staticClass:"font-medium-24"},[e._v("用户总量")]),a("p",[a("span",[e._v(e._s(e.userNum))])])]),a("div",{staticClass:"line"}),a("div",{staticClass:"outline-amount-right"},[a("p",{staticClass:"font-medium-24"},[e._v("资源总量")]),a("p",[a("span",[e._v(e._s(e.resourceNum))])])])])]),e._m(1),a("div",{staticClass:"container"},[e._m(2),a("div",{staticClass:"container-top"},[a("div",{staticClass:"categoryRank"},[a("p",{staticClass:"font-medium-24"},[e._v("TOP 5 省")]),e._l(e.topCategory,(function(t,r){return a("el-button",{key:r,staticClass:"font-regular-18",attrs:{type:"pri"},on:{click:function(a){return e.getRoleData(t)}}},[e._v(e._s(t))])}))],2)])]),a("inner-footer")],1)},n=[function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:"outline-title"},[a("p",{staticClass:"font-medium-32"},[e._v("数据统计")])])},function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:"main"},[a("div",{staticClass:"trendMain"},[a("span",{staticClass:"mainTitle font-medium-24"},[e._v("物资占比")]),a("div",{staticClass:"com-chart",attrs:{id:"main"}})])])},function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:"container-pie"},[a("p",{staticClass:"font-medium-24"},[e._v("用户占比")]),a("div",{staticClass:"com-chart",attrs:{id:"pie"}})])}],i=(a("13d5"),a("d3b7"),a("ddb0"),a("b85c")),s=(a("96cf"),a("1da1"));function o(e,t,a){return t in e?Object.defineProperty(e,t,{value:a,enumerable:!0,configurable:!0,writable:!0}):e[t]=a,e}var c=a("313e"),u=a.n(c),l={data:function(){var e;return e={pieColor:["#FFD500","#DF6C4F","#0091FF","#302FA6","#FFD500","#DF6C4F","#0091FF","#302FA6","#FFD500","#DF6C4F","#0091FF","#302FA6"],resourceNum:0,userNum:0},o(e,"resourceNum",0),o(e,"headFlag",!1),o(e,"showChoice",!1),o(e,"selectTypes",["期间图像新增数量","期间图像访问数量"]),o(e,"currentSelectedType",0),o(e,"allData",null),o(e,"pieData",null),o(e,"topCategory",["广东","北京","上海","重庆","台湾"]),o(e,"roleData",[{name:"普通用户",value:0},{name:"生产商",value:0},{name:"医院",value:0}]),o(e,"option",{tooltip:{trigger:"item"},series:[{type:"pie",radius:"50%",color:this.pieColor,data:[{value:3,name:"用户"},{value:4,name:"生产商"},{value:5,name:"医院"}],emphasis:{itemStyle:{shadowBlur:10,shadowOffsetX:0,shadowColor:"rgba(0, 0, 0, 0.5)"}}}]}),e},computed:{showTitle:function(){return this.selectTypes[this.currentSelectedType]}},methods:{getHead:function(){var e=window.sessionStorage.getItem("token");this.headFlag=!!e},initPieChart:function(){var e=document.getElementById("pie"),t=u.a.init(e);this.option.series[0].color=this.pieColor,t.setOption(this.option)},getUserList:function(){var e=this;return Object(s["a"])(regeneratorRuntime.mark((function t(){var a;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return t.next=2,e.$http.get("user/list");case 2:if(a=t.sent,200===a.data.status){t.next=6;break}return console.log(a),t.abrupt("return",e.$message.error("获取用户列表失败"));case 6:e.userNum=a.data.data.length;case 7:case"end":return t.stop()}}),t)})))()},getRCnt:function(){var e=this;return Object(s["a"])(regeneratorRuntime.mark((function t(){var a;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return t.next=2,e.$http.get("user/roleCnt");case 2:if(a=t.sent,200===a.data.status){t.next=6;break}return console.log(a),t.abrupt("return",e.$message.error("获取资源总数失败"));case 6:e.resourceNum=a.data.data.resource;case 7:case"end":return t.stop()}}),t)})))()},getRoleData:function(e){var t=this;return Object(s["a"])(regeneratorRuntime.mark((function a(){var r,n,s,o,c;return regeneratorRuntime.wrap((function(a){while(1)switch(a.prev=a.next){case 0:return t.roleData[0].value=0,t.roleData[1].value=0,t.roleData[2].value=0,a.next=5,t.$http.get("/user/list?province="+e);case 5:if(r=a.sent,200===r.data.status){a.next=9;break}return console.log(r),a.abrupt("return",t.$message.error("获取省份信息失败"));case 9:n=r.data.data,s=Object(i["a"])(n),a.prev=11,s.s();case 13:if((o=s.n()).done){a.next=24;break}if(c=o.value,"factory"!==c.role){a.next=18;break}return t.roleData[1].value+=1,a.abrupt("continue",22);case 18:if("hospital"!==c.role){a.next=21;break}return t.roleData[2].value+=1,a.abrupt("continue",22);case 21:"user"===c.role&&(t.roleData[0].value+=1);case 22:a.next=13;break;case 24:a.next=29;break;case 26:a.prev=26,a.t0=a["catch"](11),s.e(a.t0);case 29:return a.prev=29,s.f(),a.finish(29);case 32:t.option.series[0].data=t.roleData,t.option.series[0].color=t.pieColor,t.initPieChart(),console.log(t.roleData);case 36:case"end":return a.stop()}}),a,null,[[11,26,29,32]])})))()}},created:function(){this.getHead(),this.getUserList(),this.getRCnt()},mounted:function(){var e=this;return Object(s["a"])(regeneratorRuntime.mark((function t(){var a,r,n,s,o,c,l,d,p,f,m;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:e.initPieChart(),a={},r=document.getElementById("main"),n=u.a.init(r),o=["left","right","top","bottom","inside","insideTop","insideLeft","insideRight","insideBottom","insideTopLeft","insideTopRight","insideBottomLeft","insideBottomRight"],a.configParameters={rotate:{min:-90,max:90},align:{options:{left:"left",center:"center",right:"right"}},verticalAlign:{options:{top:"top",middle:"middle",bottom:"bottom"}},position:{options:o.reduce((function(e,t){return e[t]=t,e}),{})},distance:{min:0,max:100}},a.config={rotate:90,align:"left",verticalAlign:"middle",position:"insideBottom",distance:15,onChange:function(){var e={normal:{rotate:a.config.rotate,align:a.config.align,verticalAlign:a.config.verticalAlign,position:a.config.position,distance:a.config.distance}};n.setOption({series:[{label:e},{label:e},{label:e},{label:e}]})}},{show:!0,position:a.config.position,distance:a.config.distance,align:a.config.align,verticalAlign:a.config.verticalAlign,rotate:a.config.rotate,formatter:"{c}  {name|{a}}",fontSize:16,rich:{name:{}}},s={tooltip:{trigger:"axis",axisPointer:{type:"shadow"}},legend:{data:["口罩","纱布","创可贴","防护服"]},toolbox:{show:!0,orient:"vertical",left:"right",top:"center",feature:{mark:{show:!0},dataView:{show:!0,readOnly:!1},magicType:{show:!0,type:["line","bar","stack","tiled"]},restore:{show:!0},saveAsImage:{show:!0}}},xAxis:[{type:"category",axisTick:{show:!1},data:["广东","上海","台湾","重庆","北京"]}],yAxis:[{type:"value"}],series:[{color:e.pieColor[0],name:"口罩",type:"bar",barGap:0,emphasis:{focus:"series"},data:[0,0,0,0,0]},{color:e.pieColor[1],name:"纱布",type:"bar",emphasis:{focus:"series"},data:[0,0,0,0,0]},{color:e.pieColor[2],name:"创可贴",type:"bar",emphasis:{focus:"series"},data:[0,0,0,0,0]},{color:e.pieColor[3],name:"防护服",type:"bar",emphasis:{focus:"series"},data:[0,0,0,0,0]}]},t.t0=regeneratorRuntime.keys(s.xAxis[0].data);case 10:if((t.t1=t.t0()).done){t.next=23;break}return c=t.t1.value,t.next=14,e.$http.get("chain/resources/province?province="+s.xAxis[0].data[c]);case 14:if(l=t.sent,200===l.data.status){t.next=18;break}return console.log(l),t.abrupt("return",e.$message.error("物资占比-获取信息失败"));case 18:d=l.data.data,p=Object(i["a"])(d);try{for(p.s();!(f=p.n()).done;)m=f.value,"口罩"===m.category&&(s.series[0].data[c]+=m.resourceNum),"纱布"===m.category&&(s.series[1].data[c]+=m.resourceNum),"创可贴"===m.category&&(s.series[2].data[c]+=m.resourceNum),"防护服"===m.category&&(s.series[3].data[c]+=m.resourceNum)}catch(g){p.e(g)}finally{p.f()}t.next=10;break;case 23:s&&n.setOption(s);case 24:case"end":return t.stop()}}),t)})))()}},d=l,p=(a("55b3"),a("2877")),f=Object(p["a"])(d,r,n,!1,null,"f2a8bd72",null);t["default"]=f.exports},a640:function(e,t,a){"use strict";var r=a("d039");e.exports=function(e,t){var a=[][e];return!!a&&r((function(){a.call(null,t||function(){throw 1},1)}))}},d58f:function(e,t,a){var r=a("1c0b"),n=a("7b0b"),i=a("44ad"),s=a("50c4"),o=function(e){return function(t,a,o,c){r(a);var u=n(t),l=i(u),d=s(u.length),p=e?d-1:0,f=e?-1:1;if(o<2)while(1){if(p in l){c=l[p],p+=f;break}if(p+=f,e?p<0:d<=p)throw TypeError("Reduce of empty array with no initial value")}for(;e?p>=0:d>p;p+=f)p in l&&(c=a(c,l[p],p,u));return c}};e.exports={left:o(!1),right:o(!0)}}}]);