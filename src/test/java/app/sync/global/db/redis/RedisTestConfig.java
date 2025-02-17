package app.sync.global.db.redis;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.TestConfiguration;
import redis.embedded.RedisServer;
import java.io.IOException;

@Slf4j
@TestConfiguration
public class RedisTestConfig {
    private RedisServer redisServer;
    private final int port = 6379;

    @PostConstruct
    public void postConstruct() throws IOException {
        this.redisServer = new RedisServer(this.isRedisRunning() ? this.getAvailablePort() : this.port);
        this.redisServer.start();
    }

    @PreDestroy
    public void preDestroy() throws IOException {
        if (this.redisServer != null) {
            this.redisServer.stop();
        }
    }

    private Boolean isRedisRunning() throws IOException {
        return this.isProcessRunning(getProcess(this.port));
    }

    /**
     * 사용할 수 있는 포트를 조회한다.
     */
    private Integer getAvailablePort() throws IOException {
        for (int port = 10000; port <= 65535; port++) {
            Process process = this.getProcess(port);

            if (!this.isProcessRunning(process)) {
                return port;
            }
        }

        throw new IllegalArgumentException("Not Found Available Port: 10000 ~ 65535");
    }

    /**
     * LISTENING 상태인 포트의 프로세스를 조회한다.
     * (netstat -ano: 사용 중인 포트 확인)
     */
    private Process getProcess(int port) throws IOException {
        String[] command = {"cmd.exe", "/c", String.format("netstat -ano | findstr LISTENING | findstr %d", port)};

        return new ProcessBuilder(command)
                .redirectErrorStream(true)
                .start();
    }

    /**
     * 해당 프로세스가 실행 중인지 확인한다.
     */
    private Boolean isProcessRunning(Process process) {
        if (process == null) {
            return false;
        }

        return process.isAlive();
    }
}