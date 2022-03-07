package monte.service.user.client;

import monte.api.model.audit.Audit;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "audit-client", url = "${client.audit.url}")
public interface AuditClient {

    @RequestMapping(method = RequestMethod.POST)
    void sendNotification(@RequestBody Audit audit);

}
