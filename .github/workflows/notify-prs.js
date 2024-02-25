const axios = require('axios');

async function notifyPRs(SLACK_WEBHOOK_URL, repoOwner, repoName) {
  const response = await axios.get(`https://api.github.com/repos/${repoOwner}/${repoName}/pulls?state=open&sort=created&direction=asc`);
  const unreviewedPRs = response.data.filter(pr => pr.requested_reviewers.length === 0);

  const prMessages = unreviewedPRs.map(pr => {
    const createdAt = new Date(pr.created_at);
    const currentDate = new Date();
    const daysSinceCreation = Math.floor((currentDate - createdAt) / (1000 * 60 * 60 * 24));

    const prLink = `<https://github.com/${repoOwner}/${repoName}/pull/${pr.number}|${pr.title}>`;

    return `*PR 제목:* ${prLink}\n*작성자:* ${pr.user.login}\n*등록일로부터 경과 일수:* ${daysSinceCreation}일\n---`;
  });

  return {
    text: `리뷰가 필요한 PR이 ${unreviewedPRs.length}개 있습니다.\n\n${prMessages.join('\n')}`,
  };
}

module.exports = notifyPRs;
