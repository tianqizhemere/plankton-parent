/*
 * 接口统一集成模块
 */
import * as login from './moudules/login'
import * as user from './moudules/user'
import * as role from './moudules/role'
import * as menu from './moudules/menu'
import * as dict from './moudules/dict'
import * as log from './moudules/log'
import * as sys from './moudules/jvm'
import * as admin from './moudules/wechatAppAdmin'
import * as version from './moudules/version'
import * as external from './moudules/external'
import * as notice from './moudules/notice'
import * as exceptionLog from './moudules/exceptionLog'


// 默认全部导出
export default {
    login,
    user,
    role,
    menu,
    dict,
    log,
    sys,
    admin,
    version,
    external,
    notice,
    exceptionLog
}
