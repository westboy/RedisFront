package com.redisfront.commons.util;

import cn.hutool.extra.ssh.JschRuntimeException;
import cn.hutool.extra.ssh.JschUtil;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.redisfront.commons.exception.RedisFrontException;
import com.redisfront.commons.func.Fn;
import com.redisfront.model.ConnectInfo;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.models.partitions.RedisClusterNode;

/**
 * JSchUtil
 * 32768 - 61000
 *
 * @author Jin
 */
public class JschUtils {

    static ThreadLocal<Session> sessionThreadLocal = new ThreadLocal<>();

    public static Session createSession(ConnectInfo connectInfo) {
        if (Fn.isNotEmpty(connectInfo.sshConfig().privateKeyPath()) && Fn.isNotEmpty(connectInfo.sshConfig().password())) {
            return JschUtil.createSession(connectInfo.sshConfig().host(), connectInfo.sshConfig().port(), connectInfo.sshConfig().user(), connectInfo.sshConfig().privateKeyPath(), connectInfo.sshConfig().password().getBytes());
        } else if (Fn.isNotEmpty(connectInfo.sshConfig().privateKeyPath())) {
            return JschUtil.createSession(connectInfo.sshConfig().host(), connectInfo.sshConfig().port(), connectInfo.sshConfig().user(), connectInfo.sshConfig().privateKeyPath(), null);
        } else {
            return JschUtil.createSession(connectInfo.sshConfig().host(), connectInfo.sshConfig().port(), connectInfo.sshConfig().user(), connectInfo.sshConfig().password());
        }
    }

    private static String getRemoteAddress(ConnectInfo connectInfo) {
        String remoteAddress = connectInfo.host();
        if (Fn.equal(remoteAddress, "127.0.0.1") || Fn.equal(remoteAddress.toLowerCase(), "localhost")) {
            remoteAddress = connectInfo.sshConfig().host();
        }
        return remoteAddress;
    }


    public static void closeSession() {
        Session session = sessionThreadLocal.get();
        if (session != null) {
            session.disconnect();
            sessionThreadLocal.remove();
        }
    }

    public synchronized static void openSession(ConnectInfo connectInfo, RedisClusterClient clusterClient) {
        if (Fn.isNotNull(connectInfo.sshConfig())) {
            try {
                Session session = createSession(connectInfo);
                if (Fn.isNotNull(session)) {
                    session.disconnect();
                }
                session = createSession(connectInfo);

                String remoteAddress = getRemoteAddress(connectInfo);
                session.connect();
                for (RedisClusterNode partition : clusterClient.getPartitions()) {
                    Integer remotePort = partition.getUri().getPort();
                    partition.getUri().setPort(connectInfo.localPort() - remotePort);
                    JschUtil.bindPort(sessionThreadLocal.get(), remoteAddress, remotePort, partition.getUri().getPort());
                }
                sessionThreadLocal.set(session);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RedisFrontException(e, true);
            }
        } else {
            clusterClient.getPartitions().forEach(redisClusterNode -> redisClusterNode.getUri().setHost(connectInfo.host()));
        }
    }

    public synchronized static void openSession(ConnectInfo connectInfo) {
        if (Fn.isNotNull(connectInfo.sshConfig())) {
            try {
                Session session = createSession(connectInfo);
                if (Fn.isNotNull(session)) {
                    session.disconnect();
                }
                session = createSession(connectInfo);
                String remoteAddress = getRemoteAddress(connectInfo);
                session.connect();
                JschUtil.bindPort(session, remoteAddress, connectInfo.port(), connectInfo.port());
                sessionThreadLocal.set(session);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RedisFrontException(e, true);
            }
        }
    }

}