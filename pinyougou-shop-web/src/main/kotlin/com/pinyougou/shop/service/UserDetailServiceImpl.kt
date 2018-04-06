package com.pinyougou.shop.service

import com.alibaba.dubbo.config.annotation.Reference
import com.pinyougou.sellergoods.service.SellerService
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import redis.clients.util.JedisURIHelper.getPassword
import com.sun.deploy.util.SearchPath.findOne
import com.pinyougou.pojo.TbSeller
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.util.ArrayList


/**
 * @author 钟未鸣
 * @date 2018/4/3
 */
class UserDetailServiceImpl : UserDetailsService {
    @Reference
    lateinit var service: SellerService

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String?): UserDetails? {
        val seller = service.findOne(username)

        if (seller != null) {
            val auths = ArrayList<GrantedAuthority>()
            //给认证User添加权限角色
            auths.add(GrantedAuthority { "ROLE_SELLER" })
            return User(seller.sellerId, seller.password, auths)
        }
        return null

    }
}