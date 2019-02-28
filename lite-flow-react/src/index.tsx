import 'reflect-metadata';
import React, {Component} from 'react';
import ReactDOM from 'react-dom';
import {useStrict} from 'mobx'
import {createHashHistory} from "history";
import Routes from './router'
import {Router,useRouterHistory} from "react-router";
import {SysUserModel} from "./modules/app/model/SysUserModel";
import {inject} from "./common/utils/IOC";
import {appContainer} from './common/utils/Request'
import {SysInfoModel} from "./modules/app/model/SysInfoModel";
import "./themes/theme.less";
import zhCN from 'antd/lib/locale-provider/zh_CN';
import {LocaleProvider} from "antd";


useStrict(true);

const history = useRouterHistory(createHashHistory)({ queryKey: false });

class AppContainer extends Component<{}, {}> {
    @inject(SysUserModel)
    private sysUserModel: SysUserModel;

    @inject(SysInfoModel)
    private sysInfoModel: SysInfoModel;

    render(){
        appContainer(this.sysInfoModel);
        return <div><Routes history={history} sysUserModel={this.sysUserModel} /></div>
    }
}

ReactDOM.render((
    <LocaleProvider locale={zhCN}>
        <AppContainer/>
    </LocaleProvider>

), document.getElementById("root"));
